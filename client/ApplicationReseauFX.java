package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;        // attention à l'import automatique de VS Code !
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Carte;
import reseau.Protocole;
import vue.MainVue;
import vue.PlateauVue;
import vue.ScoresVue;
import javafx.scene.layout.StackPane;

import java.util.ArrayDeque;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ApplicationReseauFX extends Application {

    private Stage fenetre;
    private Connexion connexion;
    private final EtatClient etat = new EtatClient();

    private Label statut;          // écran de connexion
    private Label info;            // écran de jeu
    private PlateauVue plateau;    // null tant que l'écran de jeu n'existe pas
    private MainVue mainVue;
    private ScoresVue scores;
    private StackPane racineJeu;

    // ===== la file d'attente des messages =====
    private final ArrayDeque<String> file = new ArrayDeque<>();
    private boolean enPause = false;

    private int valeurAAnimer = -1;
    private int ramasseur = -1;
    private int gain = 0;

    private boolean deconnecte = false;


    @Override
    public void start(Stage fenetre) {
        this.fenetre = fenetre;
        fenetre.setTitle("Six qui prend - en ligne");
        fenetre.setScene(creerEcranConnexion());
        fenetre.show();
    }

    // ===== ÉCRAN 1 : la connexion =====
    private Scene creerEcranConnexion() {
        Label titre = new Label("Six qui prend - en ligne");
        titre.getStyleClass().add("titre-accueil");
        titre.getStyleClass().add("texte-table");

        TextField champAdresse = new TextField("localhost");     // valeur par défaut : le serveur sur ce PC
        TextField champPseudo = new TextField();
        champPseudo.setPromptText("Ton pseudo");

        champAdresse.setMaxWidth(300);
        champPseudo.setMaxWidth(300);

        statut = new Label("");
        statut.getStyleClass().add("texte-table");

        Button boutonRejoindre = new Button("Rejoindre");
        boutonRejoindre.setOnAction(e -> seConnecter(champAdresse.getText(), champPseudo.getText()));

        VBox racine = new VBox(15, titre, champAdresse, champPseudo, boutonRejoindre, statut);
        racine.setAlignment(Pos.CENTER);
        racine.getStyleClass().add("table");
        return creerScene(racine);
    }

    private void seConnecter(String adresse, String pseudo) {
        try {
            if (connexion == null) {
                connexion = new Connexion(adresse, 5555, message -> recevoir(message));
            }
            connexion.envoyer("NOM " + pseudo);
            statut.setText("Connecté ! En attente des autres joueurs...");
        } catch (IOException ex) {
            statut.setText("Impossible de joindre le serveur " + adresse);
        }
    }

    // ===== LE CŒUR : chaque ligne reçue du serveur passe ici =====
    private void traiterMessage(String message) {
        System.out.println("← " + message);
        etat.traiter(message);
        String commande = Protocole.commande(message);

        // une erreur AVANT la partie (pseudo invalide, partie complète) : on l'affiche sur l'écran 1
        if (plateau == null && commande.equals("ERREUR")) {
            statut.setText(etat.getDerniereErreur());
            return;
        }

        if (plateau == null && commande.equals("MAIN")) {
            creerEcranJeu();
        }

        if (commande.equals("POSE")) {
            int[] valeurs = Protocole.decoderValeurs(Protocole.donnees(message));
            valeurAAnimer = valeurs[1];
        } else if (commande.equals("RAMASSE")) {
            int[] valeurs = Protocole.decoderValeurs(Protocole.donnees(message));
            ramasseur = valeurs[0];
            gain = valeurs[1];
        }

        if (commande.equals("DECONNECTE")) {
            deconnecte = true;
            if (plateau == null) {
                statut.setText("Connexion perdue avec le serveur");
                connexion = null;
                return;
            }
            // pendant la partie : on laisse continuer, le rafraîchissement appellera mettreAJourInfo
        }

        if (plateau != null) {
            boolean finEtape = commande.equals("SCORES") && valeurAAnimer != -1;
            
            if (finEtape) {
                plateau.rafraichir(new Carte(valeurAAnimer));
            } else {
                plateau.rafraichir();
            }
            mainVue.rafraichir();
            scores.rafraichir();

            if (finEtape && ramasseur != -1) {
                scores.afficherGain(ramasseur, gain);
            }
            mettreAJourInfo();

            if (finEtape) {
                int duree = 400;
                if (ramasseur != -1) {
                    duree = 1300;
                }
                valeurAAnimer = -1;     // l'étape est finie : on oublie
                ramasseur = -1;
                mettreEnPause(duree);
            }
        }
    }

    // ===== ÉCRAN 2 : la partie =====
    private void creerEcranJeu() {
        info = new Label("La partie commence !");
        info.getStyleClass().add("texte-table");

        scores = new ScoresVue(etat);
        plateau = new PlateauVue(etat, numeroSerie -> { 
            if (deconnecte || !etat.doisChoisirSerie()) {
                return;
            }
            connexion.envoyer("SERIE " + numeroSerie);
            etat.aChoisiUneSerie();
            mettreAJourInfo();
        });
        plateau.setPadding(new Insets(20));
        mainVue = new MainVue(etat, numero -> {
            if (!etat.estMonTour()) {
                return;
            }
            Carte choisie = etat.getMaMain().getCarte(numero);
            connexion.envoyer("CARTE " + choisie.getValeur());
            etat.aJoueUneCarte();
            mettreAJourInfo();
        });  
        

        BorderPane ecran = new BorderPane();
        ecran.setPadding(new Insets(15));
        ecran.setTop(info);
        ecran.setCenter(plateau);
        ecran.setBottom(mainVue);
        ecran.setRight(scores);

        ecran.getStyleClass().add("table");

        racineJeu = new StackPane(ecran);
        fenetre.setScene(creerScene(racineJeu));
    }

    private Scene creerScene(Parent racine) {
        Scene scene = new Scene(racine, 900, 620);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        return scene;
    }

    private void mettreAJourInfo() {
        if (deconnecte == true) {
            info.setText("Connexion perdue avec le serveur");
            mainVue.setDisable(true);
            return;
        }
        if (etat.getClassement() != null) {
            info.setText("Partie terminée !");
        } else if (etat.doisChoisirSerie()) {
            info.setText("Ta carte est trop petite : clique sur la série à ramasser");
        } else if (etat.getQuiChoisit() != -1) {
            info.setText(etat.getNom(etat.getQuiChoisit()) + " choisit la série à ramasser...");
        } else if (etat.estMonTour()) {
            info.setText("À toi de jouer : choisis une carte");
        } else {
            info.setText("En attente des autres joueurs...");
        }

        mainVue.setDisable(!etat.estMonTour());
    }

    private void recevoir(String message) {
        file.add(message);
        traiterFile();
    }

    private void traiterFile() {
        while(!enPause && !file.isEmpty()) {
            traiterMessage(file.pollFirst());
        }
    }

    private void mettreEnPause(int millis) {
        enPause = true;
        PauseTransition pause = new PauseTransition(Duration.millis(millis));
        pause.setOnFinished(e -> {
            enPause = false;
            traiterFile();
        });
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
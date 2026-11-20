import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Carte;
import modele.Jeu;
import modele.Joueur;
import modele.TypeBot;
import vue.MainVue;
import vue.PlateauVue;
import vue.ScoresVue;
import javafx.scene.Parent;

public class ApplicationFX extends javafx.application.Application {
    
    // ===== ATTRIBUTS : tout ce que plusieurs méthodes doivent voir =====
    private Jeu jeu;
    private PlateauVue plateau;
    private MainVue mainVue;
    private Label info;
    private ScoresVue scores;
    private Button boutonSuivant;
    private Button boutonRejouer;

    private Stage fenetre;
    private Scene sceneAccueil;
    
    // ===== MÉTHODE 1 : construire l'écran =====
    @Override
    public void start(Stage fenetre) {
        this.fenetre = fenetre;
        sceneAccueil = creerAccueil();
        fenetre.setTitle("Six qui prend");
        fenetre.setScene(sceneAccueil);
        fenetre.show();
    }

    private Scene creerAccueil() {
        Label titre = new Label("Six qui prend");
        titre.getStyleClass().add("titre-accueil");

        Spinner<Integer> choixNbBots = new Spinner<>(1, 9, 3);

        ComboBox<TypeBot> choixNiveau = new ComboBox<>();
        choixNiveau.getItems().addAll(TypeBot.values());           // toutes les valeurs de l'enum
        choixNiveau.setValue(TypeBot.MOYEN);

        Button boutonJouer = new Button("Jouer");
        boutonJouer.setOnAction(e -> lancerPartie(choixNbBots.getValue(), choixNiveau.getValue()));

        VBox racine = new VBox(15,
            titre,
            new Label("Nombre de bots :"), choixNbBots,
            new Label("Niveau des bots :"), choixNiveau,
            boutonJouer);
        racine.setAlignment(Pos.CENTER);

        return creerScene(racine);
    }

    private void lancerPartie(int nbBots, TypeBot niveau) {
        jeu = new Jeu(10);                              // pas de type devant : on remplit l'attribut
        Joueur moi = new Joueur("Moi", false);
        jeu.ajouterJoueur(moi);

        for (int i = 1; i <= nbBots; i++) {
            jeu.ajouterJoueur(new Joueur("Bot " + i, true, niveau));
        }
        jeu.preparerManche();

        scores = new ScoresVue(jeu);

        boutonSuivant = new Button("Manche suivante");
        boutonSuivant.setVisible(false);
        boutonSuivant.setOnAction(e -> {
            jeu.preparerManche();
            boutonSuivant.setVisible(false);
            mettreAJour(false);
        }); 

        boutonRejouer = new Button("Nouvelle Partie");
        boutonRejouer.setVisible(false);
        boutonRejouer.setOnAction(e -> fenetre.setScene(sceneAccueil));

        VBox droite = new VBox(15, scores, boutonSuivant, boutonRejouer);

        info = new Label("Choisis une carte");

        plateau = new PlateauVue(jeu, numeroSerie -> {
            if (!jeu.attendChoixSerie()) {
                return;
            }
            boolean attente = jeu.choisirSerieHumain(numeroSerie);
            mettreAJour(attente);
        });
        plateau.setPadding(new Insets(20));

        mainVue = new MainVue(moi, numero -> {
            Carte choisie = moi.getMain().prendreUneCarte(numero);
            boolean attente = jeu.jouerTour(moi, choisie);
            mettreAJour(attente);
        });

        BorderPane ecran = new BorderPane();
        ecran.setPadding(new Insets(15));
        ecran.setTop(info);
        ecran.setCenter(plateau);
        ecran.setBottom(mainVue);
        ecran.setRight(droite);

        fenetre.setScene(creerScene(ecran));
    }

    // ===== MÉTHODE 2 : remettre l'écran à jour après chaque action =====
    private void mettreAJour(boolean attente) {
        plateau.rafraichir();
        mainVue.rafraichir();
        scores.rafraichir();
        mainVue.setDisable(attente);

        if (attente) {
            info.setText("Ta carte est trop petite : clique sur la série à ramasser");
        } else {
            info.setText("À toi de jouer");
        }

        if (!attente && jeu.estMancheFinie()) {
            finirManche();
        }
    }

    private void finirManche() {
        jeu.terminerManche();
        scores.rafraichir();

        if (jeu.estFinie()) {
            if (jeu.estEgalite()) {
                info.setText("Partie terminée : égalité !");
                boutonRejouer.setVisible(true);
            } else {
                Joueur gagnant = jeu.trouverGagnant();
                info.setText("Partie terminée : " + gagnant.getNom() + " gagne avec " + gagnant.getNombreDePoints() + " têtes !");
                boutonRejouer.setVisible(true);
            }
        } else {
            info.setText("Manche terminée !");
            boutonSuivant.setVisible(true);
        }
    }

    private Scene creerScene(Parent racine) {
        Scene scene = new Scene(racine, 900, 620);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        return scene;
    }
    // ===== MÉTHODE 3 : lancer le programme =====
    public static void main(String[] args) {
        launch(args);
    }
}
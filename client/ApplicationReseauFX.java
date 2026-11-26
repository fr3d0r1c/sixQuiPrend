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
import reseau.Protocole;
import vue.MainVue;
import vue.PlateauVue;
import vue.ScoresVue;
import javafx.scene.layout.StackPane;

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
            connexion = new Connexion(adresse, 5555, message -> recevoir(message));
            connexion.envoyer("NOM " + pseudo);
            statut.setText("Connecté ! En attente des autres joueurs...");
        } catch (IOException ex) {
            statut.setText("Impossible de joindre le serveur " + adresse);
        }
    }

    // ===== LE CŒUR : chaque ligne reçue du serveur passe ici =====
    private void recevoir(String message) {
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

        if (plateau != null) {
            plateau.rafraichir();
            mainVue.rafraichir();
            scores.rafraichir();
        }
    }

    // ===== ÉCRAN 2 : la partie =====
    private void creerEcranJeu() {
        info = new Label("La partie commence !");
        info.getStyleClass().add("texte-table");

        scores = new ScoresVue(etat);
        plateau = new PlateauVue(etat, numeroSerie -> { });   // les clics : en 4.6d-2
        plateau.setPadding(new Insets(20));
        mainVue = new MainVue(etat, numero -> { });           // les clics : en 4.6d-2

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

    public static void main(String[] args) {
        launch(args);
    }
}
package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConsoleReseauFX extends Application {

    private TextArea journal;
    private Connexion connexion;

    @Override
    public void start(Stage fenetre) throws IOException {
        journal = new TextArea();
        journal.setEditable(false);

        TextField saisie = new TextField();
        saisie.setPromptText("Tape un message (ex. NOM Fred), puis Entrée");

        // ce que fait la connexion à chaque message reçu (déjà sur le fil de JavaFX)
        connexion = new Connexion("localhost", 5555, message -> afficher("← " + message));

        // Entrée dans le champ : on envoie, on affiche, puis on vide le champ
        saisie.setOnAction(e -> {
            String texte = saisie.getText();
            connexion.envoyer(texte);
            afficher("→ " + texte);
            saisie.clear();                  // vide le champ
        });

        BorderPane racine = new BorderPane();
        racine.setPadding(new Insets(10));
        racine.setCenter(journal);
        racine.setBottom(saisie);

        fenetre.setTitle("Console réseau - Six qui prend");
        fenetre.setScene(new Scene(racine, 600, 450));
        fenetre.show();
    }

    private void afficher(String ligne) {
        journal.appendText(ligne + "\n");  // ajoute à la fin, puis va à la ligne
    }

    public static void main(String[] args) {
        launch(args);
    }
}

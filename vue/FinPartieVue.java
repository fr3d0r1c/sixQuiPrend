package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.Jeu;
import modele.Joueur;

public class FinPartieVue extends VBox {

    public FinPartieVue(Jeu jeu, Runnable quandRejouer, Runnable quandQuitter) {
        super(15);
        setAlignment(Pos.CENTER);
        getStyleClass().add("fin-partie");

        // 1. le classement
        Joueur[] classement = jeu.getClassement();

        // 2. le titre : as-tu gagné ?
        Joueur moi = jeu.getJoueur(0);
        String texteTitre;
        if (classement[0] == moi) {
            texteTitre = "Victoire !";
        } else {
            texteTitre = "Partie terminée";
        }
        Label titre = new Label(texteTitre);
        titre.getStyleClass().add("titre-fin");
        getChildren().add(titre);

        // 3. les lignes du classement, dans un panneau clair
        VBox panneau = new VBox(8);
        panneau.getStyleClass().add("panneau-scores");      // la classe existe déjà
        panneau.setMaxWidth(320);                           // sinon il prend toute la largeur

        for (int i = 0; i < classement.length; i++) {
            Joueur joueur = classement[i];
            Label ligne = new Label((i + 1) + ". " + joueur.getNom() + " - " + joueur.getNombreDePoints() + " 🐮");

            ligne.getStyleClass().add("ligne-fin");
            ligne.getStyleClass().add("couleur-" + jeu.positionDe(joueur));

            if (i == 0) {
                ligne.getStyleClass().add("premier");
            }
            panneau.getChildren().add(ligne);
        }
        getChildren().add(panneau);

        // 4. les deux boutons
        Button boutonRejouer = new Button("Rejouer");
        boutonRejouer.setOnAction(e -> quandRejouer.run());

        Button boutonQuitter = new Button("Quitter");
        boutonQuitter.setOnAction(e -> quandQuitter.run());

        HBox boutons = new HBox(15, boutonRejouer, boutonQuitter);
        boutons.setAlignment(Pos.CENTER);
        getChildren().add(boutons);
    }
}
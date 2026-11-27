package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.InfosPartie;

public class FinPartieVue extends VBox {

    public FinPartieVue(InfosPartie infos, int[] classement, Runnable quandRejouer, Runnable quandQuitter) {

        super(15);
        setAlignment(Pos.CENTER);
        getStyleClass().add("fin-partie");

        String texteTitre = "Fin de Partie";
        if (infos.estMoi(classement[0])) {
            texteTitre = "Victoire !";
        }
        Label titre = new Label(texteTitre); 
        titre.getStyleClass().add("titre-fin");
        getChildren().add(titre);
        
        // 3. les lignes du classement, dans un panneau clair
        VBox panneau = new VBox(8);
        panneau.getStyleClass().add("panneau-scores");      // la classe existe déjà
        panneau.setMaxWidth(320);                           // sinon il prend toute la largeur

        for (int i = 0; i < classement.length; i++) {
            int position = classement[i];
            Label ligne = new Label((i + 1) + ". " + infos.getNom(position) + " - " + infos.getScore(position) + " 🐮");

            ligne.getStyleClass().add("ligne-fin");
            ligne.getStyleClass().add("couleur-" + position);

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

        HBox boutons = new HBox(15);
        if (quandRejouer != null) {
            boutons.getChildren().add(boutonRejouer);
        }
        boutons.getChildren().add(boutonQuitter);
        boutons.setAlignment(Pos.CENTER);
        getChildren().add(boutons);
    }
}
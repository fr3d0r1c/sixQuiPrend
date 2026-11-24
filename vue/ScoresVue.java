package vue;
import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.InfosPartie;

public class ScoresVue extends VBox {

    private final InfosPartie infos;

    public ScoresVue(InfosPartie infos) {
        super(10);
        this.infos = infos;
        getStyleClass().add("panneau-scores");
        rafraichir();
    }

    public void rafraichir() {
        getChildren().clear();

        Label titre = new Label("Scores");
        titre.getStyleClass().add("titre-scores");
        getChildren().add(titre);

        for (int i = 0; i < infos.getNombreJoueurs(); i++) {
            
            Label ligne = new Label(infos.getNom(i) + " : " + infos.getScore(i) + " 🐮 (+" + infos.getTetesPile(i) + ")");

            ligne.getStyleClass().add("ligne-score");

            if (infos.estMoi(i)) {
                ligne.getStyleClass().add("ligne-score-moi");
            }

            ligne.getStyleClass().add("couleur-" + i);

            HBox boite = new HBox(6, ligne);
            getChildren().add(boite);
        }
    }

    public void afficherGain(int position, int gain) {
        HBox ligne = (HBox) getChildren().get(position + 1);

        Label texteGain = new Label("+" + gain + " 🐮");
        texteGain.getStyleClass().add("gain");
        ligne.getChildren().add(texteGain);

        // il monte de 20 pixels…
        TranslateTransition montee = new TranslateTransition(Duration.millis(1200), texteGain);
        montee.setByY(-20);
        
        FadeTransition disparition = new FadeTransition(Duration.millis(1200), texteGain);
        disparition.setFromValue(1);
        disparition.setToValue(0);

        ParallelTransition animation = new ParallelTransition(montee, disparition);
        animation.setOnFinished(e -> ligne.getChildren().remove(texteGain));   // il se supprime à la fin
        animation.play();
    }
}

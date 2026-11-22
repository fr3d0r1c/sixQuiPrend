package vue;
import javafx.animation.TranslateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.Jeu;
import modele.Joueur;

public class ScoresVue extends VBox {

    private final Jeu jeu;

    public ScoresVue(Jeu jeu) {
        super(10);
        this.jeu = jeu;
        getStyleClass().add("panneau-scores");
        rafraichir();
    }

    public void rafraichir() {
        getChildren().clear();

        Label titre = new Label("Scores");
        titre.getStyleClass().add("titre-scores");
        getChildren().add(titre);

        for (int i = 0; i < jeu.getNombreJoueurs(); i++) {
            Joueur joueur = jeu.getJoueur(i);

            Label ligne = new Label(joueur.getNom() + " : " + joueur.getNombreDePoints() + " 🐮 (+" + joueur.getPile().calculPoints() + ")");

            ligne.getStyleClass().add("ligne-score");

            if (joueur.estAvatar() == false) {
                ligne.getStyleClass().add("ligne-score-moi");
            }

            ligne.getStyleClass().add("couleur-" + i);

            HBox boite = new HBox(6, ligne);
            getChildren().add(boite);
        }
    }

    public void afficherGain(Joueur joueur, int gain) {
        int position = jeu.positionDe(joueur);
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
        animation.play()
        
        ;
    }
}

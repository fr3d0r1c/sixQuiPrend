package vue;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modele.Jeu;
import modele.Joueur;

public class ScoresVue extends VBox {

    private final Jeu jeu;

    public ScoresVue(Jeu jeu) {
        super(10);
        this.jeu = jeu;
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

            getChildren().add(ligne);
        }
    }
}

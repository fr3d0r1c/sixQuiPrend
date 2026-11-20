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
        titre.setStyle("-fx-font-size: 15px; -fx-font-weight : bold;");
        getChildren().add(titre);

        for (int i = 0; i < jeu.getNombreJoueurs(); i++) {
            Joueur joueur = jeu.getJoueur(i);

            Label ligne = new Label(joueur.getNom() + " : " + joueur.getNombreDePoints() + " 🐮 (+" + joueur.getPile().calculPoints() + ")");

            if (joueur.estAvatar() == false) {
                ligne.setStyle("-fx-font-size: 15px; -fx-font-weight : bold;");
            } else {
                ligne.setStyle("-fx-font-size: 15px;");
            }

            getChildren().add(ligne);
        }
    }
}

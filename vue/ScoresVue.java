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
        getChildren().add(titre);

        for (int i = 0; i < jeu.getNombreJoueurs(); i++) {
            Joueur joueur = jeu.getJoueur(i);

            Label nom = new Label("Nom : " + joueur.getNom());
            nom.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
            Label score = new Label("Score : " + joueur.getNombreDePoints());
            Label tetes = new Label("+ " + joueur.getPile().calculPoints() + " 🐮 cette manche");

            VBox texte = new VBox(4, nom, score, tetes);

            getChildren().add(texte);
        }
    }
}

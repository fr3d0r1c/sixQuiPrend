package vue;

import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import modele.Joueur;
import modele.Main;

public class MainVue extends HBox {
    
    private final Joueur joueur;
    private final Consumer<Integer> quandOnClique;

    public MainVue(Joueur joueur, Consumer<Integer> quandOnClique) {
        super(6);
        this.joueur = joueur;
        this.quandOnClique = quandOnClique;
        rafraichir();
    }

    public void rafraichir() {
        getChildren().clear();
        Main main = joueur.getMain();

        for (int i = 0; i < main.nombreDeCartes(); i++) {
            final int j = i;
            CarteVue vue = new CarteVue(main.getCarte(i));
            vue.setCursor(Cursor.HAND);

            vue.setOnMouseClicked(e -> quandOnClique.accept(j));

            getChildren().add(vue);
        }
    }
}

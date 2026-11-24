package vue;

import java.util.function.Consumer;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import modele.InfosPartie;
import modele.Main;

public class MainVue extends HBox {
    
    private final InfosPartie infos;
    private final Consumer<Integer> quandOnClique;

    public MainVue(InfosPartie infos, Consumer<Integer> quandOnClique) {
        super(6);
        this.infos = infos;
        this.quandOnClique = quandOnClique;
        rafraichir();
    }

    public void rafraichir() {
        getChildren().clear();
        Main main = infos.getMaMain();
        if (main == null) {
            return;
        }

        for (int i = 0; i < main.nombreDeCartes(); i++) {
            final int j = i;
            CarteVue vue = new CarteVue(main.getCarte(i));
            vue.setCursor(Cursor.HAND);

            vue.setOnMouseClicked(e -> quandOnClique.accept(j));

            getChildren().add(vue);
        }
    }
}

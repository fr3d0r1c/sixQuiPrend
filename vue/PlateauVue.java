package vue;
import java.util.function.Consumer;
import javafx.scene.Cursor;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.Jeu;
import modele.Serie;

public class PlateauVue extends VBox {
    
    private final Jeu jeu;
    private final Consumer<Integer> quandOnCliqueSerie;

    public PlateauVue(Jeu jeu, Consumer<Integer> quandOnCliqueSerie) {
        super(10);
        this.jeu = jeu;
        this.quandOnCliqueSerie = quandOnCliqueSerie;
        rafraichir();
    }

    public void rafraichir() {
        getChildren().clear();

        for (int i = 0; i < 4; i++) {
            Serie serie = jeu.getSerie(i);

            HBox ligne = new HBox(8);  
            ligne.setAlignment(Pos.CENTER_LEFT);

            Label titre = new Label("Série " + (i+1) + " (" + serie.calculPoints() + " 🐮)");
            titre.setMinWidth(90);
            ligne.getChildren().add(titre);

            for (int k = 0; k < serie.nombreDeCartes(); k++) {
                ligne.getChildren().add(new CarteVue(serie.getCarte(k)));
            }

            final int numeroSerie = i;
            ligne.setOnMouseClicked(e -> quandOnCliqueSerie.accept(numeroSerie));

            ligne.getStyleClass().add("serie");

            if (jeu.attendChoixSerie()) {
                ligne.setCursor(Cursor.HAND);
                ligne.getStyleClass().add("serie-attente");
            }

            getChildren().add(ligne);
        }
    }
}

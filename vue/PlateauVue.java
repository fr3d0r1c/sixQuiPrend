package vue;
import java.util.function.Consumer;
import javafx.scene.Cursor;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.Serie;
import modele.Carte;
import modele.InfosPartie;
import javafx.scene.control.Tooltip;

import javafx.animation.*;
import javafx.util.Duration;

public class PlateauVue extends VBox {
    
    private final InfosPartie infos;
    private final Consumer<Integer> quandOnCliqueSerie;

    public PlateauVue(InfosPartie infos, Consumer<Integer> quandOnCliqueSerie) {
        super(10);
        this.infos = infos;
        this.quandOnCliqueSerie = quandOnCliqueSerie;
        rafraichir();
    }

    public void rafraichir() {
        rafraichir(null);
    }

    public void rafraichir(Carte carteAAnimer) {
        getChildren().clear();

        for (int i = 0; i < 4; i++) {
            Serie serie = infos.getSerie(i);

            HBox ligne = new HBox(8);  
            ligne.setAlignment(Pos.CENTER_LEFT);

            Label titre = new Label("Série " + (i+1) + " (" + serie.calculPoints() + " 🐮)");
            titre.setMinWidth(90);
            titre.getStyleClass().add("texte-table");
            ligne.getChildren().add(titre);

            for (int k = 0; k < serie.nombreDeCartes(); k++) {
                CarteVue vueCarte = new CarteVue(serie.getCarte(k));
                int poseur = infos.getPoseur(vueCarte.getCarte().getValeur());

                if (poseur != -1) {
                    vueCarte.getStyleClass().add("halo-" + poseur);
                    Tooltip.install(vueCarte, new Tooltip("Posée par " + infos.getNom(poseur)));
                }

                if (carteAAnimer != null
                        && vueCarte.getCarte().getValeur() == carteAAnimer.getValeur()) {
                    animerApparition(vueCarte);
                }

                ligne.getChildren().add(vueCarte);
            }

            final int numeroSerie = i;
            ligne.setOnMouseClicked(e -> quandOnCliqueSerie.accept(numeroSerie));

            ligne.getStyleClass().add("serie");

            if (infos.doisChoisirSerie()) {
                ligne.setCursor(Cursor.HAND);
                ligne.getStyleClass().add("serie-attente");
            }

            getChildren().add(ligne);
        }
    }
    
    private void animerApparition(CarteVue vueCarte) {
        ScaleTransition zoom = new ScaleTransition(Duration.millis(300), vueCarte);
        zoom.setFromX(0.3);
        zoom.setFromY(0.3);
        zoom.setToX(1);
        zoom.setToY(1);

        FadeTransition fondu = new FadeTransition(Duration.millis(300), vueCarte);
        fondu.setFromValue(0);
        fondu.setToValue(1);

        new ParallelTransition(zoom, fondu).play();
    }
}

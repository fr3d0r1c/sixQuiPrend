package vue;
import java.util.function.Consumer;
import javafx.scene.Cursor;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modele.CarteJouee;
import modele.Jeu;
import modele.Serie;
import modele.Carte;
import modele.Joueur;
import javafx.scene.control.Tooltip;

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
                CarteVue vueCarte = new CarteVue(serie.getCarte(k));
                Joueur poseur = trouverPoseur(vueCarte.getCarte());

                if (poseur != null) {
                    vueCarte.getStyleClass().add("halo-" + jeu.positionDe(poseur));
                    Tooltip.install(vueCarte, new Tooltip("Posée par " + poseur.getNom()));
                }
                ligne.getChildren().add(vueCarte);
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

    private boolean estDansDernierTour(Carte carte) {
        CarteJouee[] tour = jeu.getDernierTour();
        if (tour == null) {
            return false;
        }
        for (int i = 0; i < tour.length; i++) {
            if (tour[i].getCarte() == carte) {
                return true;
            }
        }
        return false;
    }

    private Joueur trouverPoseur(Carte carte) {
        CarteJouee[] tour = jeu.getDernierTour();
        if (tour == null) {
            return null;
        }
        for (int i = 0; i < tour.length; i++) {
            if (tour[i].getCarte() == carte) {
                return tour[i].getJoueur();
            }
        }
        return null;
    }
}

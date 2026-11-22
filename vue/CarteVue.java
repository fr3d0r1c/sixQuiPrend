package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modele.Carte;

public class CarteVue extends StackPane {
    
    private final Carte carte;

     public CarteVue(Carte carte) {
        this.carte = carte;

        // le fond : un rectangle aux coins arrondis
        Rectangle fond = new Rectangle(50, 75);
        fond.setArcWidth(12);
        fond.setArcHeight(12);
        fond.getStyleClass().add("tetes-" + carte.getNombreTetesDeBoeuf());
        fond.getStyleClass().add("carte-fond");

        // le texte : la valeur en grand, les têtes en dessous
        Label valeur = new Label(String.valueOf(carte.getValeur()));
        valeur.getStyleClass().add("valeur-carte");
        Label tetes = new Label(carte.getNombreTetesDeBoeuf() + " 🐮");

        VBox textes = new VBox(4, valeur, tetes);
        textes.setAlignment(Pos.CENTER);

        getChildren().addAll(fond, textes);
    }

    public Carte getCarte() {
        return carte;
    }
}

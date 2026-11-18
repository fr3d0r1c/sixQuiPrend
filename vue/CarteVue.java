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
        Rectangle fond = new Rectangle(60, 90);
        fond.setArcWidth(12);
        fond.setArcHeight(12);
        fond.setFill(couleurSelonTetes(carte.getNombreTetesDeBoeuf()));
        fond.setStroke(Color.BLACK);

        // le texte : la valeur en grand, les têtes en dessous
        Label valeur = new Label(String.valueOf(carte.getValeur()));
        valeur.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Label tetes = new Label(carte.getNombreTetesDeBoeuf() + " 🐮");

        VBox textes = new VBox(4, valeur, tetes);
        textes.setAlignment(Pos.CENTER);

        getChildren().addAll(fond, textes);
    }

    public Carte getCarte() {
        return carte;
    }

    private Color couleurSelonTetes(int nbTetes) {
        switch (nbTetes) {
            case 1: return Color.WHITE;
            case 2: return Color.LIGHTBLUE;
            case 3: return Color.GOLD;   // à toi de choisir
            case 5: return Color.DARKORANGE;
            case 7: return Color.MEDIUMPURPLE;
            default: throw new IllegalArgumentException("Nombre de têtes impossible : " + nbTetes);
        }
    }
}

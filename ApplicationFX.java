import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import modele.Carte;
import vue.CarteVue;

public class ApplicationFX extends javafx.application.Application {
    private int compteur = 0;
    
    @Override
    public void start(Stage fenetre) {
        Label message = new Label("Six qui prend");
        Button bouton = new Button("Jouer");

        bouton.setOnAction(e -> {
            compteur++;
            message.setText("Cliqué " + compteur + " fois");
        });

        VBox racine = new VBox(10, message, bouton); // empile verticalement, 10 px d'écart
        HBox rangee = new HBox(10,
            new CarteVue(new Carte(1)),
            new CarteVue(new Carte(5)),
            new CarteVue(new Carte(10)),
            new CarteVue(new Carte(11)),
            new CarteVue(new Carte(55))
        );
        rangee.setPadding(new Insets(20));
        Scene scene = new Scene(rangee, 400, 300);

        fenetre.setTitle("Six qui prend");
        fenetre.setScene(scene);
        fenetre.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

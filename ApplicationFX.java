import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationFX extends javafx.application.Application {
    int compteur = 0;
    
    @Override
    public void start(Stage fenetre) {
        Label message = new Label("Six qui prend");
        Button bouton = new Button("Jouer");

        bouton.setOnAction(e -> {
            compteur++;
            message.setText("Cliqué " + compteur + " fois");
        });

        VBox racine = new VBox(10, message, bouton);   // empile verticalement, 10 px d'écart
        Scene scene = new Scene(racine, 400, 300);

        fenetre.setTitle("Six qui prend");
        fenetre.setScene(scene);
        fenetre.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Carte;
import modele.Jeu;
import modele.Joueur;
import modele.TypeBot;
import modele.CarteJouee;
import vue.CarteVue;
import vue.PlateauVue;
public class ApplicationFX extends javafx.application.Application {
    private int compteur = 0;
    
    @Override
    public void start(Stage fenetre) {
        Label message = new Label("Six qui prend");
        Button bouton = new Button("Jouer");

        Jeu jeu = new Jeu(2);
        Joueur moi = new Joueur("Moi", false);
        Joueur bot = new Joueur("Bot 1", true, TypeBot.MOYEN);
        jeu.ajouterJoueur(moi);
        jeu.ajouterJoueur(bot);

        jeu.preparerManche();
        PlateauVue plateau = new PlateauVue(jeu);
        plateau.setPadding(new Insets(20));

        bouton.setOnAction(e -> {
            jeu.poserCarteBot(new CarteJouee(jeu.choisirCarteBot(bot), bot));
            plateau.rafraichir();
        });

        VBox ecran = new VBox(plateau, bouton);
        ecran.setPadding(new Insets(20));

        Scene scene = new Scene(ecran, 700, 500);

        fenetre.setTitle("Six qui prend");
        fenetre.setScene(scene);
        fenetre.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

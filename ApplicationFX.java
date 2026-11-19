import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Carte;
import modele.Jeu;
import modele.Joueur;
import modele.TypeBot;
import modele.CarteJouee;
import vue.CarteVue;
import vue.MainVue;
import vue.PlateauVue;
public class ApplicationFX extends javafx.application.Application {
    private int compteur = 0;
    private MainVue mainVue;
    
    @Override
    public void start(Stage fenetre) {
        // 1. LE MODÈLE (inchangé)
        Jeu jeu = new Jeu(2);
        Joueur moi = new Joueur("Moi", false);
        Joueur bot = new Joueur("Bot 1", true, TypeBot.MOYEN);
        jeu.ajouterJoueur(moi);
        jeu.ajouterJoueur(bot);
        jeu.preparerManche();

        jeu.preparerManche();
        PlateauVue plateau = new PlateauVue(jeu);
        plateau.setPadding(new Insets(20));

        Label info = new Label("Choisis une carte");

        mainVue = new MainVue(moi, numero -> {
            Carte choisie = moi.getMain().prendreUneCarte(numero);   // a. retirer la carte de la main
            jeu.jouerTour(moi, choisie);                                     // b. jouer le tour complet
            info.setText("Tu as joué le " + choisie.getValeur());    // c. informer le joueur
            plateau.rafraichir();                                         // d. redessiner le plateau
            mainVue.rafraichir();                                           //    et la main
        });

        BorderPane ecran = new BorderPane();
        ecran.setPadding(new Insets(15));
        ecran.setTop(info);
        ecran.setCenter(plateau);
        ecran.setBottom(mainVue);

        Scene scene = new Scene(ecran, 900, 620);
        fenetre.setTitle("Six qui prend");
        fenetre.setScene(scene);
        fenetre.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

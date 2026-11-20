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
import vue.MainVue;
import vue.PlateauVue;
import vue.ScoresVue;
public class ApplicationFX extends javafx.application.Application {
    
    // ===== ATTRIBUTS : tout ce que plusieurs méthodes doivent voir =====
    private Jeu jeu;
    private PlateauVue plateau;
    private MainVue mainVue;
    private Label info;
    private ScoresVue scores;
    private Button boutonSuivant;
    
    // ===== MÉTHODE 1 : construire l'écran =====
    @Override
    public void start(Stage fenetre) {
        jeu = new Jeu(2);                              // pas de type devant : on remplit l'attribut
        Joueur moi = new Joueur("Moi", false);
        Joueur bot = new Joueur("Bot 1", true, TypeBot.MOYEN);
        jeu.ajouterJoueur(moi);
        jeu.ajouterJoueur(bot);
        jeu.preparerManche();

        scores = new ScoresVue(jeu);

        boutonSuivant = new Button("Manche suivante");
        boutonSuivant.setVisible(false);
        boutonSuivant.setOnAction(e -> {
            jeu.preparerManche();
            boutonSuivant.setVisible(false);
            mettreAJour(false);
        }); 

        VBox droite = new VBox(15, scores, boutonSuivant);

        info = new Label("Choisis une carte");

        plateau = new PlateauVue(jeu, numeroSerie -> {
            if (!jeu.attendChoixSerie()) {
                return;
            }
            boolean attente = jeu.choisirSerieHumain(numeroSerie);
            mettreAJour(attente);
        });
        plateau.setPadding(new Insets(20));

        mainVue = new MainVue(moi, numero -> {
            Carte choisie = moi.getMain().prendreUneCarte(numero);
            boolean attente = jeu.jouerTour(moi, choisie);
            mettreAJour(attente);
        });

        BorderPane ecran = new BorderPane();
        ecran.setPadding(new Insets(15));
        ecran.setTop(info);
        ecran.setCenter(plateau);
        ecran.setBottom(mainVue);
        ecran.setRight(droite);

        Scene scene = new Scene(ecran, 900, 620);
        fenetre.setTitle("Six qui prend");
        fenetre.setScene(scene);
        fenetre.show();
    }

    // ===== MÉTHODE 2 : remettre l'écran à jour après chaque action =====
    private void mettreAJour(boolean attente) {
        plateau.rafraichir();
        mainVue.rafraichir();
        scores.rafraichir();
        mainVue.setDisable(attente);

        if (attente) {
            info.setText("Ta carte est trop petite : clique sur la série à ramasser");
        } else {
            info.setText("À toi de jouer");
        }

        if (!attente && jeu.estMancheFinie()) {
            finirManche();
        }
    }

    private void finirManche() {
        jeu.terminerManche();
        scores.rafraichir();

        if (jeu.estFinie()) {
            if (jeu.estEgalite()) {
                info.setText("Partie terminée : égalité !");
            } else {
                Joueur gagnant = jeu.trouverGagnant();
                info.setText("Partie terminée : " + gagnant.getNom() + " gagne avec " + gagnant.getNombreDePoints() + " têtes !");
            }
        } else {
            info.setText("Manche terminée !");
            boutonSuivant.setVisible(true);
        }
    }

    // ===== MÉTHODE 3 : lancer le programme =====
    public static void main(String[] args) {
        launch(args);
    }
}
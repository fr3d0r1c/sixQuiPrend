package modele;
import java.util.Random;

public class BotAleatoire implements StrategieBot {
    private Random r = new Random();

    @Override
    public Carte choisirCarte(Joueur j, Jeu jeu) {
        int numCarte = r.nextInt(j.getMain().nombreDeCartes());
        return j.getMain().prendreUneCarte(numCarte);
    }

    @Override
    public int choisirSerie(Joueur j, Jeu jeu) {
        return r.nextInt(4);
    }
}

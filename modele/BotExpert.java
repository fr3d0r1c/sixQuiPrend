package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BotExpert implements StrategieBot {

    private static final int NB_SIMULATIONS = 200;
    private final Random hasard = new Random();

    @Override
    public Carte choisirCarte(Joueur j, Jeu jeu) {
        Main main = j.getMain();
        ArrayList<Integer> inconnues = jeu.cartesInconnues(j);
        int nbAdversaires = jeu.getNombreJoueurs() - 1;

        int meilleurIndex = 0;
        double meilleureMoyenne = Double.MAX_VALUE;

        for (int i = 0; i < main.nombreDeCartes(); i++) {
            int maCarte = main.getCarte(i).getValeur();

            int total = 0;
            for (int s = 0; s < NB_SIMULATIONS; s++) {
                int[] cartesAdverses = tirerCartes(inconnues, nbAdversaires);
                total = total + SimulateurTour.coutPour(jeu, maCarte, cartesAdverses);
            }

            double moyenne = (double) total / NB_SIMULATIONS;
            if (moyenne < meilleureMoyenne) {
                meilleureMoyenne = moyenne;
                meilleurIndex = i;
            }
        }

        return main.prendreUneCarte(meilleurIndex);
    }

    private int[] tirerCartes(ArrayList<Integer> inconnues, int n) {
        Collections.shuffle(inconnues, hasard);     // on mélange la liste…
        int[] cartes = new int[n];
        for (int i = 0; i < n; i++) {
            cartes[i] = inconnues.get(i);         // …et on prend les n premières
        }
        return cartes;
    }

    @Override
    public int choisirSerie(Joueur j, Jeu jeu) {
        return jeu.trouverSerieMoinsChere();                           // comme MOYEN et DIFFICILE
    }
}

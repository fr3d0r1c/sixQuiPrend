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
        double meilleurRisque = Double.MAX_VALUE;
        int tailleMain = main.nombreDeCartes();

        for (int i = 0; i < main.nombreDeCartes(); i++) {
            int maCarte = main.getCarte(i).getValeur();

            int total = 0;
            for (int s = 0; s < NB_SIMULATIONS; s++) {
                int[] cartesAdverses = tirerCartesRealistes(jeu, inconnues, nbAdversaires, tailleMain);
                total = total + SimulateurTour.coutPour(jeu, maCarte, cartesAdverses);
            }

            double moyenne = (double) total / NB_SIMULATIONS;
            double risqueCarte = risque(jeu, main.getCarte(i));

            boolean meilleure = moyenne < meilleureMoyenne;
            boolean egaliteMaisPlusSure = moyenne == meilleureMoyenne && risqueCarte < meilleurRisque;
            if (meilleure || egaliteMaisPlusSure) {
                meilleureMoyenne = moyenne;
                meilleurRisque = risqueCarte;
                meilleurIndex = i;
            }
        }

        return main.prendreUneCarte(meilleurIndex);
    }

    private int[] tirerCartesRealistes(Jeu jeu, ArrayList<Integer> inconnues, int nbAdversaires, int tailleMain) {
        Collections.shuffle(inconnues, hasard);
        int[] cartes = new int[nbAdversaires];

        for (int a = 0; a < nbAdversaires; a++) {
            cartes[a] = choisirCommeMoyen(jeu, inconnues, a * tailleMain, tailleMain);
        }
        return cartes;
    }

    public static int choisirCommeMoyen(Jeu jeu, ArrayList<Integer> cartes, int debut, int taille) {
        int meilleureCarte = -1;
        int meilleurEcart = Integer.MAX_VALUE;
        int plusPetite = Integer.MAX_VALUE;

        for (int k = debut; k < debut + taille; k++) {
            int v = cartes.get(k);
            
            if (v < plusPetite) {
                plusPetite = v;
            }

            int numeroSerie = jeu.trouverSerie(new Carte(v));
            if (numeroSerie == -1) {
                continue;
            }
            Serie serie = jeu.getSerie(numeroSerie);
            if (serie.nombreDeCartes() == 5) {
                continue;
            }
            int ecart = v - serie.derniereCarte().getValeur();
            if (ecart < meilleurEcart) {
                meilleurEcart = ecart;
                meilleureCarte = v;
            }
        }

        if (meilleureCarte == -1) {
            meilleureCarte = plusPetite;
        }
        return meilleureCarte;
    }

    private double risque(Jeu jeu, Carte carte) {
        int numeroSerie = jeu.trouverSerie(carte);
        if (numeroSerie == -1) {
            return Double.MAX_VALUE;
        }
        Serie serie = jeu.getSerie(numeroSerie);
        if (serie.nombreDeCartes() == 5) {
            return Double.MAX_VALUE;
        }
        int ecart = carte.getValeur() - serie.derniereCarte().getValeur();
        int placesRestantes = 5 - serie.nombreDeCartes();
        return (double) ecart / placesRestantes;
    }

    @Override
    public int choisirSerie(Joueur j, Jeu jeu) {
        return jeu.trouverSerieMoinsChere();                           // comme MOYEN et DIFFICILE
    }
}

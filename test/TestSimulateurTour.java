package test;

import modele.*;

public class TestSimulateurTour {
    public static void main(String[] args) {
        TestSimulateurTour monTest = new TestSimulateurTour();
        monTest.testSansRamassage();
        monTest.testMaSixiemeCarte();
        monTest.testAdversaireMeDevance();
        monTest.testCarteTropPetite();
        monTest.testJeuIntact();
    }

    // séries 10 | 20 | 30 | 40, comme dans TestJeu
    private Jeu creerJeuAvecSeries() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));
        return jeu;
    }

    private void testSansRamassage() {
        // PRÉPARER
        Jeu jeu = creerJeuAvecSeries();

        // AGIR : je joue le 22, un adversaire joue le 35
        int cout = SimulateurTour.coutPour(jeu, 22, new int[] { 35 });

        // VÉRIFIER
        Test.test(cout == 0, "le 22 se pose après le 20 : aucune tête ramassée");
    }

    private void testMaSixiemeCarte() {
        // PRÉPARER : la série 1 contient déjà 16, 17, 18, 19, 20 (7 têtes)
        Jeu jeu = creerJeuAvecSeries();
        jeu.getSerie(1).vider();
        for (int v = 16; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }

        // AGIR : je joue le 22, seul
        int cout = SimulateurTour.coutPour(jeu, 22, new int[] { });

        // VÉRIFIER
        Test.test(cout == 7, "mon 22 est la 6e carte : je ramasse les 7 têtes");
    }

    private void testAdversaireMeDevance() {
        // PRÉPARER : la série 1 contient 17, 18, 19, 20 (4 cartes : il reste UNE place)
        Jeu jeu = creerJeuAvecSeries();
        jeu.getSerie(1).vider();
        for (int v = 17; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }

        // AGIR : je joue le 23, un adversaire joue le 21
        int cout = SimulateurTour.coutPour(jeu, 23, new int[] { 21 });

        // VÉRIFIER
        Test.test(cout == 7, "le 21 prend la dernière place : mon 23 est la 6e carte (7 têtes)");
    }

    private void testCarteTropPetite() {
        Jeu jeu = creerJeuAvecSeries();

        int cout = SimulateurTour.coutPour(jeu, 5, new int[] { });

        Test.test(cout == 3, "le 5 est trop petit : je ramasse la série la moins chère (3 têtes)");
    }

    private void testJeuIntact() {
        // PRÉPARER : même situation que la 6e carte
        Jeu jeu = creerJeuAvecSeries();
        jeu.getSerie(1).vider();
        for (int v = 16; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }

        // AGIR : on simule (le simulateur, lui, « ramasse » la série)
        SimulateurTour.coutPour(jeu, 22, new int[] { });

        // VÉRIFIER : le VRAI jeu n'a pas bougé
        Test.test(jeu.getSerie(1).nombreDeCartes() == 5, "la vraie série contient toujours ses 5 cartes");
        Test.test(jeu.getSerie(1).derniereCarte().getValeur() == 20, "la dernière carte de la vraie série est toujours le 20");
    }
}

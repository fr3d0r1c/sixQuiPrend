package test;

import modele.Carte;
import modele.Jeu;

public class TestJeu {
    public static void main(String[] args) {
        TestJeu monTest = new TestJeu();
        monTest.testConstructeur1();
        monTest.testConstructeur2();
		monTest.testConstructeur3();
    }

    private void testConstructeur1() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));
        Test.test(jeu.trouverSerie(new Carte(22)) == 1,
                    "une carte de valeur 22 va dans la série de dernière carte 20");
    }

    private void testConstructeur2() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));
        Test.test(jeu.trouverSerie(new Carte(5)) == -1,
                "une carte de valeur 5 va dans aucune série");
    }

    private void testConstructeur3() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));
        Test.test(jeu.trouverSerie(new Carte(99)) == 3,
                "une carte de valeur 99 va dans la série avec comme dernière carte 40");
    }
}

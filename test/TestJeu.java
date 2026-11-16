package test;

import modele.Carte;
import modele.Jeu;
import modele.Joueur;

public class TestJeu {
    public static void main(String[] args) {
        TestJeu monTest = new TestJeu();
        monTest.testConstructeur1();
        monTest.testConstructeur2();
		monTest.testConstructeur3();
        monTest.testConstructeur4();
        monTest.testConstructeur5();
        monTest.testConstructeur6();
        monTest.testConstructeur7();
        monTest.testConstructeur8();
        monTest.testConstructeur9();
        monTest.testConstructeur10();
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

    private void testConstructeur4() {
        Jeu jeu = new Jeu(2);
        Joueur joueur = new Joueur("Arthur", false);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(0).ajouterCarte(new Carte(11));
        jeu.getSerie(0).ajouterCarte(new Carte(12));

        int tetes = jeu.ramasserSerie(0, joueur, new Carte(50));

        Test.test(tetes == 9, "ramasserSerie renvoie le nombre de têtes ramassées");
        Test.test(joueur.getPile().calculPoints() == 9, "les cartes ramassées sont dans la pile du joueur");
        Test.test(jeu.getSerie(0).nombreDeCartes() == 1, "la série ne contient plus que la carte jouée");
    }

    private void testConstructeur5() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);

        j1.ajouterPoints(0);
        j2.ajouterPoints(0);

        Test.test(jeu.estFinie() == false, "la partie n'est pas finie au début (0 et 0)");

    }

    private void testConstructeur6() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);

        j1.ajouterPoints(67);
        j2.ajouterPoints(10);

        Test.test(jeu.estFinie() == true, "la partie est terminée à 67 (67 et 10)");

    }

    private void testConstructeur7() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);

        j1.ajouterPoints(66);
        j2.ajouterPoints(10);

        Test.test(jeu.estFinie() == false, "la partie n'est pas finie à 66 (66 et 10)");
    }

    private void testConstructeur8() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);

        j1.ajouterPoints(10);
        j2.ajouterPoints(80);

        Test.test(jeu.estFinie() == true, "la partie est terminée pour le second joueur (10 et 80)");

    }

    private void testConstructeur9() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        Joueur j3 = new Joueur("Bot2", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(30);
        j2.ajouterPoints(10);
        j3.ajouterPoints(20);

        Test.test(jeu.trouverGagnant() == j2, "le vainqueur est j2");
    }

    private void testConstructeur10() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        Joueur j3 = new Joueur("Bot2", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(30);
        j2.ajouterPoints(20);
        j3.ajouterPoints(10);

        Test.test(jeu.trouverGagnant() == j3, "le vainqueur est j3");
    }
}

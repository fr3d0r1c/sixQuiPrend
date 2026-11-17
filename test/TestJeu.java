package test;

import modele.Carte;
import modele.CarteJouee;
import modele.Jeu;
import modele.Joueur;
import modele.Main;
import modele.TypeBot;

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
        monTest.testConstructeur11();
        monTest.testConstructeur12();
        monTest.testConstructeur13();
        monTest.testConstructeur14();
        monTest.testConstructeur15();
        monTest.testConstructeur16();
        
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

    private void testConstructeur11() {
        Jeu jeu = new Jeu(2);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot", true);
        Joueur j3 = new Joueur("Bot2", true);

        CarteJouee[] cartesJouees = new CarteJouee[3];
        cartesJouees[0] = new CarteJouee(new Carte(50), j1);
        cartesJouees[1] = new CarteJouee(new Carte(10), j2);
        cartesJouees[2] = new CarteJouee(new Carte(30), j3);

        jeu.ordonnerCartesJouees(cartesJouees);

        // 3. Les cartes sont dans l'ordre croissant
        Test.test(cartesJouees[0].getCarte().getValeur() == 10, "la plus petite carte (10) joue en premier");
        Test.test(cartesJouees[1].getCarte().getValeur() == 30, "la carte 30 joue en deuxième");
        Test.test(cartesJouees[2].getCarte().getValeur() == 50, "la plus grande carte (50) joue en dernier");

        // 4. Chaque carte est restée avec son joueur
        Test.test(cartesJouees[0].getJoueur() == j2, "la carte 10 appartient toujours à j2");
        Test.test(cartesJouees[1].getJoueur() == j3, "la carte 30 appartient toujours à j3");
        Test.test(cartesJouees[2].getJoueur() == j1, "la carte 50 appartient toujours à j1");

    }

    private void testConstructeur12() {
        Jeu jeu = new Jeu(2);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
         bot.getMain().ajouterCarte(new Carte(10));
        bot.getMain().ajouterCarte(new Carte(11));
        bot.getMain().ajouterCarte(new Carte(12));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 10, "le bot FACILE joue sa plus petite carte");
        Test.test(bot.getMain().nombreDeCartes() == 2, "la carte jouée a été retirée de la main");
    }

    private void testConstructeur13() {
        Jeu jeu = new Jeu(2);
        boolean toujoursValide = true;

        for (int essai = 0; essai < 100; essai++) {
            Joueur bot = new Joueur("Bot", true, TypeBot.ALEATOIRE);
            bot.getMain().ajouterCarte(new Carte(10));
            bot.getMain().ajouterCarte(new Carte(11));
            bot.getMain().ajouterCarte(new Carte(12));

            Carte carteJouee = jeu.choisirCarteBot(bot);

            if (carteJouee == null) {
                toujoursValide = false;
            } else {
                int v = carteJouee.getValeur();
                if (v != 10 && v != 11 && v != 12) {
                    toujoursValide = false;
                }
            }
            if (bot.getMain().nombreDeCartes() != 2) {
                toujoursValide = false;
            }
        }

        Test.test(toujoursValide, "sur 100 essais, le bot ALEATOIRE joue toujours une carte de sa main");
    }

    private void testConstructeur14() {
        Jeu jeu = new Jeu(2);
        boolean toujoursValide = true;
        Joueur bot = new Joueur("Bot", true);

        for (int essai = 0; essai < 100; essai++) {

            int choix = jeu.choisirSerieBot(bot);

            if (choix < 0 || choix > 3 ) {
                toujoursValide = false; 
            }
        }

        Test.test(toujoursValide, "sur 100 essais, le joueur choisit toujours une série de 0 à 3.");
    }

    private void testConstructeur15() {
        Jeu jeu = new Jeu(3);
        jeu.ajouterJoueur(new Joueur("Claude", false));
        jeu.ajouterJoueur(new Joueur("Bot1", true));
        jeu.ajouterJoueur(new Joueur("Bot2", true));

        jeu.preparerManche();
        
        boolean seriesOk = true;
        for (int i = 0; i < 4; i++) {
            if (jeu.getSerie(i).nombreDeCartes() != 1) {
                seriesOk = false;
            }
        }
        Test.test(seriesOk, "chaque série commence avec une seule carte");

        boolean mainsOk = true;
        boolean mainsTriees = true;
        for (int i = 0; i < 3; i++) {
            Main main = jeu.getJoueur(i).getMain();
            if (main.nombreDeCartes() != 10) {
                mainsOk = false;
            }
            for (int c = 0; c < main.nombreDeCartes() - 1; c++) {
                if (main.getCarte(c).getValeur() > main.getCarte(c+1).getValeur()) {
                    mainsTriees = false;
                }
            }
        }
        Test.test(mainsOk, "chaque joueur reçoit 10 cartes");
        Test.test(mainsTriees, "la main de chaque joueur est triée");

        Test.test(jeu.getPioche().nombreCartesDisponibles() == 70,
                "la pioche a perdu les 34 cartes distribuées");
    }

    private void testConstructeur16() {
        Jeu jeu = new Jeu(3);
        jeu.ajouterJoueur(new Joueur("Claude", false));
        jeu.ajouterJoueur(new Joueur("Bot1", true));
        jeu.ajouterJoueur(new Joueur("Bot2", true));

        jeu.preparerManche();
        jeu.preparerManche();
        
        boolean seriesOk = true;
        for (int i = 0; i < 4; i++) {
            if (jeu.getSerie(i).nombreDeCartes() != 1) {
                seriesOk = false;
            }
        }
        Test.test(seriesOk, "après une 2e préparation, chaque série ne contient toujours qu'une carte");
    }
}

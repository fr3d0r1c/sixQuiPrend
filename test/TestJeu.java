package test;

import java.util.ArrayList;

import modele.*;

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
        monTest.testPreparerMancheDeuxFois();
        monTest.testPoserCarteBotCasNormal();
        monTest.testPoserCarteBotSixiemeCarte();
        monTest.testPoserCarteBotCarteTropPetite();
        monTest.testTerminerManche();
        monTest.testJouerPartieAutomatique();
        monTest.testEstEgalite1();
        monTest.testEstEgalite2();
        monTest.testEstEgalite3();
        monTest.testEstEgalite4();
        monTest.testBotMoyenCarteTropPetite();
        monTest.testBotMoyenEcartMinimal();
        monTest.testBotMoyenEviteSixiemeCarte();
        monTest.testBotMoyenSituDangeureuse();
        monTest.testBotMoyenRamasseSerieMoinsChereEnDernier();
        monTest.testBotMoyenRamasseSerieMoinsChereEnPremier();
        monTest.testBotDifficileSixiemeCarteMoinsChere();
        monTest.testBotDifficileCarteLaPlusSure();
        monTest.testCreerStrategie();
        monTest.testJouerTour();
        monTest.testJouerTourAttendChoixSerie();
        monTest.testEstMancheFinie();
        monTest.testPositionDe();
        monTest.testTourPasAPas();
        monTest.testDernierRamassage();
        monTest.testGetClassement();
        monTest.testDeuxHumains();
        monTest.testAChoisi();
        monTest.testGetPoseur();
        monTest.testGetMaMain();
        monTest.testGetMaMainSansHumain();
        monTest.testDevenirBot();
        monTest.testPreparerTourCarteDejaChoisie();
        monTest.testGetClassementPositions();
        monTest.testMemoireDebutManche();
        monTest.testMemoireApresTour();
        monTest.testMemoireNouvelleManche();
        monTest.testCartesInconnues();
        monTest.testBotExpertEviteSixiemeCarte();
        monTest.testBotExpertCarteTropPetite();
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

    private void testPreparerMancheDeuxFois() {
        Jeu jeu = new Jeu(3);
        Joueur humain = new Joueur("Claude", false);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(new Joueur("Bot1", true));
        jeu.ajouterJoueur(new Joueur("Bot2", true));

        jeu.preparerManche();
        boolean attente = jeu.jouerTour(humain, humain.getMain().prendreUneCarte(0));
        if (attente == true) {
            jeu.choisirSerieHumain(0);
        }
        jeu.preparerManche();
        
        boolean seriesOk = true;
        for (int i = 0; i < 4; i++) {
            if (jeu.getSerie(i).nombreDeCartes() != 1) {
                seriesOk = false;
            }
        }
        Test.test(seriesOk, "après une 2e préparation, chaque série ne contient toujours qu'une carte");
        Test.test(jeu.getDernierTour() == null, "Une nouvelle manche efface le dernier tour.");
    }

    private void testPoserCarteBotCasNormal() {
        Jeu jeu = new Jeu(2);
        Joueur bot = new Joueur("Bot", true, TypeBot.ALEATOIRE);

        jeu.ajouterJoueur(bot);

        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));

        jeu.poserCarte(new CarteJouee(new Carte(22), bot));

        Test.test(jeu.getSerie(1).nombreDeCartes() == 2, "la carte 22 s'ajoute à la série de dernière carte 20");
        Test.test(jeu.getSerie(1).derniereCarte().getValeur() == 22, "22 est maintenant la dernière carte de la série");
        Test.test(bot.getPile().calculPoints() == 0, "le bot n'a rien ramassé");
    }

    private void testPoserCarteBotSixiemeCarte() {
        // 1. PRÉPARER : la série 1 contient déjà 5 cartes
        Jeu jeu = new Jeu(2);
        Joueur bot = new Joueur("Bot", true, TypeBot.ALEATOIRE);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        for (int v = 16; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));

        // 2. AGIR
        jeu.poserCarte(new CarteJouee(new Carte(22), bot));

        // 3. VÉRIFIER
        Test.test(jeu.getSerie(1).nombreDeCartes() == 1, "après la 6e carte, la série ne contient plus que la 22");
        Test.test(jeu.getSerie(1).derniereCarte().getValeur() == 22, "la 22 démarre la nouvelle série");
        Test.test(bot.getPile().calculPoints() > 0, "le bot a ramassé les 5 cartes");
    }

    private void testPoserCarteBotCarteTropPetite() {
        // 1. PRÉPARER
        Jeu jeu = new Jeu(2);
        Joueur bot = new Joueur("Bot", true, TypeBot.ALEATOIRE);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));

        // 2. AGIR
        jeu.poserCarte(new CarteJouee(new Carte(5), bot));

        // 3. VÉRIFIER
        // On ne sait pas QUELLE série le bot a ramassée : on la cherche
        boolean trouvee = false;
        for (int i = 0; i < 4; i++) {
            Serie s = jeu.getSerie(i);
            if (s.nombreDeCartes() == 1 && s.derniereCarte().getValeur() == 5) {
                trouvee = true;
            }
        }
        Test.test(trouvee, "la carte 5 a démarré une nouvelle série");
        Test.test(bot.getPile().calculPoints() > 0, "le bot a ramassé une série");
    }

    private void testTerminerManche() {
        Jeu jeu = new Jeu(2);
        Joueur bot1 = new Joueur("Bot1", true, TypeBot.FACILE);
        Joueur bot2 = new Joueur("Bot2", true, TypeBot.FACILE);
        jeu.ajouterJoueur(bot1);
        jeu.ajouterJoueur(bot2);

        bot1.ajouterPoints(10);
        bot1.getPile().ajouterCarte(new Carte(10));
        bot1.getPile().ajouterCarte(new Carte(11));
        bot1.getPile().ajouterCarte(new Carte(12));

        jeu.terminerManche();

        Test.test(bot1.getNombreDePoints() == 19, "les points de la pile s'ajoutent au score (10+3+5+1 = 19)");
        Test.test(bot1.getPile().calculPoints() == 0, "la pile est vidée après la manche");
        Test.test(bot2.getNombreDePoints() == 0, "une pile vide n'ajoute aucun point");
    }

    private void testJouerPartieAutomatique() {
        Jeu jeu = new Jeu(4);
        boolean quelqunQuiDepasse = false;
        
        Joueur bot1 = new Joueur("Bot1", true, TypeBot.FACILE);
        Joueur bot2 = new Joueur("Bot2", true, TypeBot.ALEATOIRE);
        Joueur bot3 = new Joueur("Bot3", true, TypeBot.FACILE);
        Joueur bot4 = new Joueur("Bot4", true, TypeBot.ALEATOIRE);
        jeu.ajouterJoueur(bot1);
        jeu.ajouterJoueur(bot2);
        jeu.ajouterJoueur(bot3);
        jeu.ajouterJoueur(bot4);

        jeu.jouerPartieAutomatique();

        for (int i = 0; i < jeu.getNombreJoueurs(); i++) {
            if (jeu.getJoueur(i).getNombreDePoints() > 66) {
                quelqunQuiDepasse = true;
            }
        }

        Test.test(jeu.estFinie() == true, "La partie est finie");
        Test.test(quelqunQuiDepasse, "au moins un joueur a dépassé 66 points");
        Test.test(jeu.trouverGagnant() != null, "Le gagnant n'est pas null");
    }

    private  void testEstEgalite1() {
        Jeu jeu = new Jeu(3);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot1", true);
        Joueur j3 = new Joueur("Bot2", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(10);
        j2.ajouterPoints(20);
        j3.ajouterPoints(30);

        jeu.estEgalite();

        Test.test(jeu.estEgalite() == false, "Un seul gagnant");
    }

    private  void testEstEgalite2() {
        Jeu jeu = new Jeu(3);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot1", true);
        Joueur j3 = new Joueur("Bot2", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(10);
        j2.ajouterPoints(10);
        j3.ajouterPoints(30);

        jeu.estEgalite();

        Test.test(jeu.estEgalite() == true, "égalité entre les deux premiers");
    }

    private  void testEstEgalite3() {
        Jeu jeu = new Jeu(3);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot1", true);
        Joueur j3 = new Joueur("Bot2", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(30);
        j2.ajouterPoints(10);
        j3.ajouterPoints(10);

        jeu.estEgalite();

        Test.test(jeu.estEgalite() == true, "égalité qui ne concerne pas le premier joueur");
    }

    private  void testEstEgalite4() {
        Jeu jeu = new Jeu(3);
        Joueur j1 = new Joueur("Claude", false);
        Joueur j2 = new Joueur("Bot1", true);
        Joueur j3 = new Joueur("Bot2", true);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(10);
        j2.ajouterPoints(20);
        j3.ajouterPoints(20);

        jeu.estEgalite();

        Test.test(jeu.estEgalite() == false, "égalité mais pas pour la première place");
    }

    private Jeu creerJeuAvecSeries() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));
        return jeu;
    }

    private void testBotMoyenEcartMinimal() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur bot = new Joueur("Bot", true, TypeBot.MOYEN);
        bot.getMain().ajouterCarte(new Carte(23));
        bot.getMain().ajouterCarte(new Carte(35));
        bot.getMain().ajouterCarte(new Carte(49));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 23, "le bot MOYEN joue la carte la plus proche d'une série (23 sur 20)");
        Test.test(bot.getMain().nombreDeCartes() == 2, "la carte jouée a été retirée de la main");
    }

    private void testBotMoyenCarteTropPetite() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur bot = new Joueur("Bot", true, TypeBot.MOYEN);
        bot.getMain().ajouterCarte(new Carte(5));
        bot.getMain().ajouterCarte(new Carte(45));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 45, "le bot MOYEN évite la carte trop petite");
    }

    private void testBotMoyenEviteSixiemeCarte() {
        Jeu jeu = creerJeuAvecSeries();  
        jeu.getSerie(1).vider();
        for (int v = 16; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }

        Joueur bot = new Joueur("Bot", true, TypeBot.MOYEN);
        bot.getMain().ajouterCarte(new Carte(22));
        bot.getMain().ajouterCarte(new Carte(48));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 48, "le bot MOYEN écarte une carte qui serait la 6ème");
    }

    private void testBotMoyenSituDangeureuse() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur bot = new Joueur("Bot", true, TypeBot.MOYEN);
        bot.getMain().ajouterCarte(new Carte(3));
        bot.getMain().ajouterCarte(new Carte(7));
        bot.getMain().ajouterCarte(new Carte(9));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 3, "si toutes ses cartes sont dangereuses, le bot MOYEN joue la plus petite");
    }

    private void testBotMoyenRamasseSerieMoinsChereEnDernier() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(55));
        jeu.getSerie(1).ajouterCarte(new Carte(10));
        jeu.getSerie(2).ajouterCarte(new Carte(11));
        jeu.getSerie(2).ajouterCarte(new Carte(13));

        Joueur bot = new Joueur("Bot", true, TypeBot.MOYEN);

        int serieChoisie = jeu.choisirSerieBot(bot);

        Test.test(serieChoisie == 3, "le bot MOYEN ramasse la série la moins chère (la dernière, 1 tête)");
    }

    private void testBotMoyenRamasseSerieMoinsChereEnPremier() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(13));
        jeu.getSerie(1).ajouterCarte(new Carte(10));
        jeu.getSerie(2).ajouterCarte(new Carte(11));
        jeu.getSerie(3).ajouterCarte(new Carte(55));

        Joueur bot = new Joueur("Bot", true, TypeBot.MOYEN);

        int serieChoisie = jeu.choisirSerieBot(bot);

        Test.test(serieChoisie == 0, "le bot MOYEN ramasse la série la moins chère (la première, 1 tête)");
    }

    private void testBotDifficileSixiemeCarteMoinsChere() {
        Jeu jeu = creerJeuAvecSeries();

        // Série 1 : 5 cartes jusqu'à 20, CHÈRE (5 + 2 + 1 + 1 + 3 = 12 têtes)
        jeu.getSerie(1).vider();
        jeu.getSerie(1).ajouterCarte(new Carte(11));   // 5 têtes
        jeu.getSerie(1).ajouterCarte(new Carte(15));   // 2 têtes
        jeu.getSerie(1).ajouterCarte(new Carte(18));   // 1 tête
        jeu.getSerie(1).ajouterCarte(new Carte(19));   // 1 tête
        jeu.getSerie(1).ajouterCarte(new Carte(20));   // 3 têtes

        // Série 3 : 5 cartes jusqu'à 40, MOINS CHÈRE (1 + 1 + 1 + 1 + 3 = 7 têtes)
        jeu.getSerie(3).vider();
        for (int v = 36; v <= 40; v++) {
            jeu.getSerie(3).ajouterCarte(new Carte(v));
        }

        Joueur bot = new Joueur("Bot", true, TypeBot.DIFFICILE);
        bot.getMain().ajouterCarte(new Carte(25));
        bot.getMain().ajouterCarte(new Carte(45));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 45, "le bot DIFFICILE choisit la 6e carte qui coûte le moins de têtes");
    }

    private void testBotDifficileCarteLaPlusSure() {
        Jeu jeu = creerJeuAvecSeries();

        jeu.getSerie(1).vider();
        for (int v = 17; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }

        Joueur bot = new Joueur("Bot", true, TypeBot.DIFFICILE);
        bot.getMain().ajouterCarte(new Carte(23));
        bot.getMain().ajouterCarte(new Carte(48));

        Carte carteJouee = jeu.choisirCarteBot(bot);

        Test.test(carteJouee.getValeur() == 48, "le bot DIFFICILE choisit la carte qui comporte le moins de risque");
    }

    private void testCreerStrategie() {
        Test.test(TypeBot.ALEATOIRE.creerStrategie() instanceof BotAleatoire, "ALEATOIRE crée un BotAleatoire");
        Test.test(TypeBot.FACILE.creerStrategie() instanceof BotFacile, "FACILE crée un BotFacile");
        Test.test(TypeBot.MOYEN.creerStrategie() instanceof BotMoyen, "MOYEN crée un BotMoyen");
        Test.test(TypeBot.DIFFICILE.creerStrategie() instanceof BotDifficile, "DIFFICILE crée un BotDifficile");
        Test.test(TypeBot.EXPERT.creerStrategie() instanceof BotExpert, "EXPERT crée un BotExpert");
    }

    private void testJouerTour() {
        // PRÉPARER : un humain et un bot, une manche préparée
        Jeu jeu = new Jeu(2);
        Joueur humain = new Joueur("Humain", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);
        jeu.preparerManche();

        Carte carte = humain.getMain().prendreUneCarte(0);

        boolean attente = jeu.jouerTour(humain, carte);
        if (attente == true) {
            jeu.choisirSerieHumain(0);
        }

        int total = jeu.getSerie(0).nombreDeCartes()
        + jeu.getSerie(1).nombreDeCartes()
        + jeu.getSerie(2).nombreDeCartes()
        + jeu.getSerie(3).nombreDeCartes()
        + humain.getPile().nombreDeCartes()
        + bot.getPile().nombreDeCartes();

        Test.test(humain.getMain().nombreDeCartes() == 9, "l'humain a 9 cartes après un tour");
        Test.test(bot.getMain().nombreDeCartes() == 9, "le bot a aussi joué une carte");
        Test.test(total == 6, "les cartes jouées sont placées ou ramassées dans une série");
        Test.test(jeu.getDernierTour().length == 2, "Apres un tour, il y a deux cartes");

        CarteJouee[] tour = jeu.getDernierTour();
        Test.test(tour[0].getCarte().getValeur() < tour[1].getCarte().getValeur(), "les cartes du dernier tour sont triées par ordre croissant");
    }

    private void testJouerTourAttendChoixSerie() {
        // PRÉPARER : séries 10, 20, 30, 40
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Humain", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);
        bot.getMain().ajouterCarte(new Carte(50));

        // AGIR 1 : l'humain joue le 5, trop petit pour toutes les séries
        boolean attente = jeu.jouerTour(humain, new Carte(5));

        // VÉRIFIER 1 : le jeu attend, et le 50 du bot n'est PAS encore posé
        Test.test(attente == true, "le jeu attend le choix de l'humain");
        Test.test(jeu.getSerie(3).nombreDeCartes() == 1, "le 50 du bot attend son tour (le 5 passe avant)");

        // AGIR 2 : l'humain choisit la série 3 (la carte 30)
        boolean encore = jeu.choisirSerieHumain(2);

        // VÉRIFIER 2 : le tour est fini, tout est à sa place
        Test.test(encore == false, "le tour est terminé après le choix");
        Test.test(humain.getPile().nombreDeCartes() == 1, "l'humain a ramassé la série du 30");
        Test.test(jeu.getSerie(2).derniereCarte().getValeur() == 5, "le 5 remplace la série ramassée");
        Test.test(jeu.getSerie(3).nombreDeCartes() == 2, "le 50 du bot a été posé après le 40");
    }

    private void testEstMancheFinie() {
        // PRÉPARER
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Humain", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);
        humain.getMain().ajouterCarte(new Carte(5));
        bot.getMain().ajouterCarte(new Carte(50));

        // 1. des cartes en main → pas finie
        Test.test(jeu.estMancheFinie() == false, "la manche n'est pas finie tant qu'il reste des cartes");

        // 2. dernière carte trop petite → mains vides, mais choix en attente
        Carte carte = humain.getMain().prendreUneCarte(0);
        jeu.jouerTour(humain, carte);
        Test.test(jeu.estMancheFinie() == false, "pas finie tant que l'humain n'a pas choisi sa série");

        // 3. après le choix → finie
        jeu.choisirSerieHumain(0);
        Test.test(jeu.estMancheFinie() == true, "la manche est finie quand tout est joué");
    }

    private void testPositionDe() {
        // PRÉPARER : 3 joueurs dans le jeu, et un 4e qui n'y est PAS
        Jeu jeu = new Jeu(3);
        Joueur moi = new Joueur("Moi", false);
        Joueur bot1 = new Joueur("Bot 1", true, TypeBot.FACILE);
        Joueur bot2 = new Joueur("Bot 2", true, TypeBot.FACILE);
        jeu.ajouterJoueur(moi);
        jeu.ajouterJoueur(bot1);
        jeu.ajouterJoueur(bot2);

        Joueur inconnu = new Joueur("Inconnu", true, TypeBot.FACILE);   // créé, mais jamais ajouté

        // AGIR et VÉRIFIER : chaque joueur retrouve sa position
        Test.test(jeu.positionDe(moi) == 0, "le premier joueur ajouté est en position 0");
        Test.test(jeu.positionDe(bot1) == 1, "le deuxième joueur ajouté est en position 1");
        Test.test(jeu.positionDe(bot2) == 2, "le troisième joueur ajouté est en position 2");

        // un joueur absent du jeu n'a pas de position
        Test.test(jeu.positionDe(inconnu) == -1, "un joueur qui n'est pas dans le jeu renvoie -1");
    }

    private void testTourPasAPas() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Humain", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);
        bot.getMain().ajouterCarte(new Carte(35));

        // 1. préparer : rien n'est encore posé
        jeu.preparerTour(humain, new Carte(22));
        Test.test(jeu.estTourEnCours(), "après la préparation, un tour est en cours");
        Test.test(!jeu.attendChoixSerie(), "le 22 n'est pas trop petit : pas d'attente");
        Test.test(jeu.getSerie(1).nombreDeCartes() == 1, "aucune carte n'est encore posée");

        // 2. première étape : le 22 (la plus petite) est posé
        Test.test(jeu.poserProchaineCarte() == EtatTour.CARTE_POSEE, "1re étape : une carte est posée");
        Test.test(jeu.getSerie(1).derniereCarte().getValeur() == 22, "le 22 est posé après le 20");
        Test.test(jeu.getDerniereCartePosee().getValeur() == 22, "La carte 22 est la dernière carte posée");

        // 3. deuxième étape : le 35 du bot
        Test.test(jeu.poserProchaineCarte() == EtatTour.CARTE_POSEE, "2e étape : une carte est posée");
        Test.test(jeu.getDernierPoseur() == bot, "Bot est le dernier poseur");

        // 4. troisième étape : plus rien à poser
        Test.test(jeu.poserProchaineCarte() == EtatTour.TOUR_FINI, "3e étape : le tour est fini");
        Test.test(!jeu.estTourEnCours(), "après la fin, plus aucun tour en cours");
    }

    private void testDernierRamassage() {
        // PRÉPARER : séries 10, 20, 30, 40 ; l'humain joue 5 (trop petit), le bot a le 50
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Humain", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);
        bot.getMain().ajouterCarte(new Carte(50));
        jeu.preparerTour(humain, new Carte(5));

        // AGIR 1 : l'humain ramasse la série du 30
        jeu.ramasserPourHumain(2);

        // VÉRIFIER 1
        Test.test(jeu.getDernierRamasseur() == humain, "l'humain est le dernier à avoir ramassé");
        Test.test(jeu.getDernierGain() == 3, "il a ramassé les têtes du 30");

        // AGIR 2 : le 50 du bot se pose sans ramasser
        jeu.poserProchaineCarte();

        // VÉRIFIER 2 : la nouvelle étape a effacé le ramassage précédent
        Test.test(jeu.getDernierRamasseur() == null, "une étape sans ramassage efface le dernier ramasseur");
    }

    private void testGetClassement() {
        Jeu jeu = new Jeu(3);
        Joueur moi = new Joueur("Moi", false);
        Joueur bot1 = new Joueur("Bot 1", true, TypeBot.FACILE);
        Joueur bot2 = new Joueur("Bot 2", true, TypeBot.FACILE);
        jeu.ajouterJoueur(moi);
        jeu.ajouterJoueur(bot1);
        jeu.ajouterJoueur(bot2);

        moi.ajouterPoints(40);
        bot1.ajouterPoints(70);
        bot2.ajouterPoints(15);

        Joueur[] classement = jeu.getClassement();

        Test.test(classement.length == 3, "le classement contient tous les joueurs");
        Test.test(classement[0] == bot2, "le 1er est celui qui a le moins de têtes (Bot 2, 15)");
        Test.test(classement[1] == moi, "le 2e est Moi (40)");
        Test.test(classement[2] == bot1, "le dernier est celui qui a le plus de têtes (Bot 1, 70)");

        // VÉRIFIER : l'ordre de la partie n'a pas bougé
        Test.test(jeu.getJoueur(0) == moi, "le classement ne modifie pas l'ordre des joueurs du jeu");
    }

    private void testDeuxHumains() {
        // PRÉPARER : séries 10, 20, 30, 40 ; deux humains et un bot
        Jeu jeu = creerJeuAvecSeries();
        Joueur alice = new Joueur("Alice", false);
        Joueur bob = new Joueur("Bob", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(alice);
        jeu.ajouterJoueur(bob);
        jeu.ajouterJoueur(bot);
        bot.getMain().ajouterCarte(new Carte(50));

        // 1. Alice choisit : il manque encore Bob
        jeu.choisirCarte(alice, new Carte(22));
        Test.test(!jeu.tousLesHumainsOntChoisi(), "tant que Bob n'a pas choisi, on attend");

        // 2. Bob choisit une carte trop petite
        jeu.choisirCarte(bob, new Carte(5));
        Test.test(jeu.tousLesHumainsOntChoisi(), "les deux humains ont choisi");

        // 3. on prépare et on joue : Bob (5) passe en premier, et doit choisir une série
        jeu.preparerTour();
        Test.test(jeu.poserProchaineCarte() == EtatTour.ATTENTE_CHOIX, "le 5 de Bob est trop petit");
        Test.test(jeu.getJoueurQuiDoitChoisir() == bob, "c'est Bob qui doit choisir, pas Alice");

        // 4. Bob ramasse, puis la suite se joue
        jeu.ramasserPourHumain(0);
        
        // pose les cartes restantes jusqu'à la fin du tour, puis vérifie :
        EtatTour etat;
        do {
            etat = jeu.poserProchaineCarte();
        } while (etat == EtatTour.CARTE_POSEE);

        Test.test(jeu.getDernierTour().length == 3, "les 3 cartes font partie du tour");
        Test.test(!jeu.tousLesHumainsOntChoisi(), "le tour suivant repart de zéro");
        Test.test(!jeu.estTourEnCours(), "Le tour est terminé");
    }

    private void testAChoisi() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur alice = new Joueur("Alice", false);
        jeu.ajouterJoueur(alice);

        Test.test(!jeu.aChoisi(alice), "au debut, Alice n'a pas choisi");
        jeu.choisirCarte(alice, new Carte(22));
        Test.test(jeu.aChoisi(alice), "après choisirCarte, Alice a choisi");
    }

    private void testGetPoseur() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Humain", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);
        bot.getMain().ajouterCarte(new Carte(35));

        Test.test(jeu.getPoseur(22) == -1, "avant tout tour, getPoseur renvoie -1");

        jeu.preparerTour(humain, new Carte(22));
        while (jeu.poserProchaineCarte() != EtatTour.TOUR_FINI) {}

        Test.test(jeu.getPoseur(22) == 0, "Le 22 a été joué par l'humain");
        Test.test(jeu.getPoseur(35) == 1, "le 35 a été posé par le bot");
        Test.test(jeu.getPoseur(99) == -1, "le 99 n'a été posé par personne");
    }

    private void testGetMaMain() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        Joueur humain = new Joueur("Humain", false);
        jeu.ajouterJoueur(bot);   // le bot d'abord !
        jeu.ajouterJoueur(humain);

        Test.test(jeu.getMaMain() == humain.getMain(), "getMaMain renvoie la main de l'humain, pas celle du bot");
        Test.test(jeu.estMoi(0) == false, "Le joueur 0 n'est pas l'humain");
        Test.test(jeu.estMoi(1) == true, "Le joueur 1 est humain");
        Test.test(jeu.getTetesPile(1) == 0, "Le joueur 1 n'au aucune carte dans sa pile");
        Test.test(jeu.getNom(0).equals("Bot"), "Le joueur 0 s'appelle Bot");
    }

    private void testGetMaMainSansHumain() {
        Jeu jeu = creerJeuAvecSeries();
        jeu.ajouterJoueur(new Joueur("Bot1", true, TypeBot.FACILE));
        jeu.ajouterJoueur(new Joueur("Bot2", true, TypeBot.FACILE));

        Test.test(jeu.getMaMain() == null, "sans humain, getMaMain renvoie null");
    }

    private void testDevenirBot() {
        Joueur alice = new Joueur("alice", false);

        alice.devenirBot(TypeBot.DIFFICILE);

        Test.test(alice.estAvatar(), "après devenirBot, alice est un bot");
        Test.test(alice.getStrategie() != null, "le nouveau bot a une stratégie");
        Test.test(alice.getTypeBot() == TypeBot.DIFFICILE, "le type du bot est celui demandé");
        Test.test(alice.getNom().equals("alice (bot)"), "le nom indique de c'est un bot");
    }

    private void testPreparerTourCarteDejaChoisie() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur alice = new Joueur("alice", false);
        jeu.ajouterJoueur(alice);
        alice.getMain().ajouterCarte(new Carte(42));
        alice.getMain().ajouterCarte(new Carte(55));
        alice.getMain().ajouterCarte(new Carte(66));

        Carte choisie = alice.getMain().prendreUneCarte(0);   // comme le serveur : le 42 quitte la main
        jeu.choisirCarte(alice, choisie);
        Test.test(jeu.aChoisi(alice), "le 42 est bien mémorisé avant le départ");
        alice.devenirBot(TypeBot.MOYEN);

        jeu.preparerTour();

        Test.test(jeu.getDernierTour()[0].getCarte().getValeur() == 42,
              "la carte choisie avant le départ (42) est bien jouée");
        Test.test(alice.getMain().nombreDeCartes() == 2,
              "le bot n'a pas pris une 2e carte dans la main");
    }

    public void testGetClassementPositions() {
        Jeu jeu = new Jeu(3);
        Joueur moi = new Joueur("Moi", false);
        Joueur bot1 = new Joueur("Bot 1", true, TypeBot.FACILE);
        Joueur bot2 = new Joueur("Bot 2", true, TypeBot.FACILE);
        jeu.ajouterJoueur(moi);
        jeu.ajouterJoueur(bot1);
        jeu.ajouterJoueur(bot2);

        moi.ajouterPoints(40);
        bot1.ajouterPoints(70);
        bot2.ajouterPoints(15);

        int[] classement = jeu.getClassementPositions();

        Test.test(classement.length == 3, "le classement contient tous les joueurs");
        Test.test(classement[0] == 2, "le 1er est celui qui a le moins de têtes (Bot 2, 15)");
        Test.test(classement[1] == 0, "le 2e est Moi (40)");
        Test.test(classement[2] == 1, "le dernier est celui qui a le plus de têtes (Bot 1, 70)");

    }

    private int compterVues(Jeu jeu) {
        int nbVues = 0;
        for (int valeur = 1; valeur <= 104; valeur++) {
            if (jeu.estVue(valeur)) {
                nbVues++;
            }
        }
        return nbVues;
    }

    private void testMemoireDebutManche() {
        Jeu jeu = new Jeu(3);
        jeu.ajouterJoueur(new Joueur("Moi", false));
        jeu.ajouterJoueur(new Joueur("Bot 1", true, TypeBot.FACILE));
        jeu.ajouterJoueur(new Joueur("Bot 2", true, TypeBot.FACILE));

        jeu.preparerManche();

        int nbVues = compterVues(jeu);
        
        Test.test(nbVues == 4, "au début d'une manche, seules les 4 cartes des séries sont vues");
        Test.test(jeu.estVue(jeu.getSerie(0).derniereCarte().getValeur()), "la carte de la série 0 est vue");
    }

    private void testMemoireApresTour() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Moi", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);

        bot.getMain().ajouterCarte(new Carte(35));
        jeu.preparerTour(humain, new Carte(22));


        Test.test(jeu.estVue(22) && jeu.estVue(35), "au début, seul le 22 et le 35 sont vues");
        Test.test(!jeu.estVue(50), "La carte 50 n'est pas vue");
    }

    private void testMemoireNouvelleManche() {
        Jeu jeu = creerJeuAvecSeries();
        Joueur humain = new Joueur("Moi", false);
        Joueur bot = new Joueur("Bot", true, TypeBot.FACILE);
        jeu.ajouterJoueur(humain);
        jeu.ajouterJoueur(bot);

        jeu.preparerManche();

        Test.test(compterVues(jeu) == 4, "au début de la manche, 4 cartes vues");

        jeu.preparerTour(humain, humain.getMain().prendreUneCarte(0));
        Test.test(compterVues(jeu) > 4, "Le tour a bien mémorisé les cartes");

        jeu.preparerManche();
        Test.test(compterVues(jeu) == 4, "La mémoire a bien été effacé");
    }

    private void testCartesInconnues() {
        Jeu jeu = new Jeu(3);
        Joueur moi = new Joueur("Moi", false);
        jeu.ajouterJoueur(moi);

        jeu.preparerManche();
        ArrayList<Integer> inconnues = jeu.cartesInconnues(moi);
        Test.test(inconnues.size() == 90, "Pour un joueur, 90 cartes lui sont inconnues");

        boolean aucuneDeSesCartes = true;
        for (int i = 0; i < moi.getMain().nombreDeCartes(); i++) {
            int valeur = moi.getMain().getCarte(i).getValeur();
            if (inconnues.contains(valeur)) {
                aucuneDeSesCartes = false;
            }
        }
        Test.test(aucuneDeSesCartes, "ses propres cartes ne sont pas inconnues pour lui");
    }

    private void testBotExpertEviteSixiemeCarte() {
        Jeu jeu = creerJeuAvecSeries();
        jeu.getSerie(1).vider();
        for (int v = 16; v <= 20; v++) {
            jeu.getSerie(1).ajouterCarte(new Carte(v));
        }

        Joueur expert = new Joueur("Bot 1", true, TypeBot.EXPERT);
        jeu.ajouterJoueur(expert);

        expert.getMain().ajouterCarte(new Carte(22));
        expert.getMain().ajouterCarte(new Carte(45));
        jeu.ajouterJoueur(new Joueur("bot 2", true, TypeBot.MOYEN));

        Carte carteJouee = jeu.choisirCarteBot(expert);

        Test.test(carteJouee.getValeur() == 45, "l'EXPERT joue le 45 et évite la 6e carte");
        Test.test(expert.getMain().nombreDeCartes() == 1, "Il reste 1 carte dans la main du joueur expert");
    }

    private void testBotExpertCarteTropPetite() {
        Jeu jeu = creerJeuAvecSeries();
        jeu.ajouterJoueur(new Joueur("Bot 1", true, TypeBot.EXPERT));
        jeu.ajouterJoueur(new Joueur("Bot 2", true, TypeBot.MOYEN));

        int serie = jeu.choisirSerieBot(jeu.getJoueur(0));

        Test.test(serie >= 0 && serie <= 3, "La serie est entre 0 et 3");
    }
}
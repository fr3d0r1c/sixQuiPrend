package test;

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
        monTest.testConstructeur16();
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

    private void testPoserCarteBotCasNormal() {
        Jeu jeu = new Jeu(2);
        Joueur bot = new Joueur("Bot", true, TypeBot.ALEATOIRE);

        jeu.ajouterJoueur(bot);

        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));

        jeu.poserCarteBot(new CarteJouee(new Carte(22), bot));

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
        jeu.poserCarteBot(new CarteJouee(new Carte(22), bot));

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
        jeu.poserCarteBot(new CarteJouee(new Carte(5), bot));

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
}

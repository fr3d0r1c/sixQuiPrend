package test;

import modele.Carte;
import modele.Jeu;
import modele.Joueur;
import modele.Main;
import modele.TypeBot;
import reseau.Protocole;

public class TestProtocole {
    
    public static void main(String[] args) {
        TestProtocole monTest = new TestProtocole();
        monTest.testCommandeEtDonnees();
        monTest.testCommandeSansDonnees();
        monTest.testEncoderMain();
        monTest.testDecoderValeurs();
        monTest.testDecoderValeursVide();
        monTest.testAllerRetourMain();
        monTest.testAllerRetourSeries();
        monTest.testAllerRetourScores();
        monTest.testAllerRetourJoueur();
    }

    private void testCommandeEtDonnees() {
        String ligne = "CARTE 42";
        Test.test(Protocole.commande(ligne).equals("CARTE"), "la commande de \"CARTE 42\" est CARTE");
        Test.test(Protocole.donnees(ligne).equals("42"), "les données de \"CARTE 42\" sont 42");
    }

    private void testCommandeSansDonnees() {
        String ligne = "FIN_MANCHE";
        Test.test(Protocole.commande(ligne).equals("FIN_MANCHE"), "sans espace, toute la ligne est la commande");
        Test.test(Protocole.donnees(ligne).equals(""), "sans espace, il n'y a aucune donnée");
    }

    private void testEncoderMain() {
        Main main = new Main();
        main.ajouterCarte(new Carte(3));
        main.ajouterCarte(new Carte(15));
        main.ajouterCarte(new Carte(27));

        Test.test(Protocole.encoderMain(main).equals("MAIN 3 15 27"), "une main de 3 cartes s'encode dans l'ordre");
    }

    private void testDecoderValeurs() {
        int[] valeurs = Protocole.decoderValeurs("3 15 27");
        Test.test(valeurs.length == 3, "3 nombres donnent un tableau de 3 cases");
        Test.test(valeurs[0] == 3 && valeurs[1] == 15 && valeurs[2] == 27, "chaque valeur est bien décodée, dans l'ordre");
    }

    private void testDecoderValeursVide() {
        int[] valeurs = Protocole.decoderValeurs("");
        Test.test(valeurs.length == 0, "aucune donnée donne un tableau vide");
    }

    private void testAllerRetourMain() {
        // PRÉPARER : une main de 4 cartes
        Main main = new Main();
        main.ajouterCarte(new Carte(8));
        main.ajouterCarte(new Carte(42));
        main.ajouterCarte(new Carte(55));
        main.ajouterCarte(new Carte(104));

        // AGIR : le serveur encode… et le client décode
        String ligne = Protocole.encoderMain(main);
        int[] valeurs = Protocole.decoderValeurs(Protocole.donnees(ligne));

        // VÉRIFIER : on retrouve exactement la même main
        Test.test(valeurs.length == main.nombreDeCartes(), "l'aller-retour garde le nombre de cartes");
        boolean identiques = true;
        for (int i = 0; i < valeurs.length; i++) {
            if (valeurs[i] != main.getCarte(i).getValeur()) {
                identiques = false;
            }
        }
        Test.test(identiques, "l'aller-retour redonne exactement les mêmes valeurs");
    }

    private Jeu creerJeuAvecSeries() {
        Jeu jeu = new Jeu(2);
        jeu.getSerie(0).ajouterCarte(new Carte(10));
        jeu.getSerie(1).ajouterCarte(new Carte(20));
        jeu.getSerie(2).ajouterCarte(new Carte(30));
        jeu.getSerie(3).ajouterCarte(new Carte(40));
        return jeu;
    }

    private void testAllerRetourSeries() {
        // PRÉPARER : séries 10, 20, 30, 40, puis une 2e carte sur la série 2
        Jeu jeu = creerJeuAvecSeries();
        jeu.getSerie(2).ajouterCarte(new Carte(35));
        
        // AGIR : encoder…
        String ligne = Protocole.encoderSeries(jeu);

        // VÉRIFIER l'encodage
        Test.test(ligne.equals("SERIES 10|20|30,35|40"), "les séries s'encodent avec | entre elles et , dans une série");

        // AGIR : …puis décoder
        int[][] series = Protocole.decoderSeries(Protocole.donnees(ligne));

        Test.test(series.length == 4, "on retrouve 4 séries");
        Test.test(series[2].length == 2, "la série 2 a bien 2 cartes");
        Test.test(series[2][0] == 30 && series[2][1] == 35, "la série 2 contient 30 puis 35");
        Test.test(series[0].length == 1 && series[0][0] == 10, "la série 0 contient seulement le 10");
    }

    private void testAllerRetourScores() {
        Jeu jeu = new Jeu(3);
        Joueur j1 = new Joueur("j1", false);
        Joueur j2 = new Joueur("j2", true, TypeBot.FACILE);
        Joueur j3 = new Joueur("j3", true, TypeBot.FACILE);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        j1.ajouterPoints(10);
        j2.ajouterPoints(15);
        j3.ajouterPoints(30);

        j1.getPile().ajouterCarte(new Carte(25));

        String ligne = Protocole.encoderScores(jeu);

        Test.test(ligne.equals("SCORES 10,2|15,0|30,0"), "Les scores s'encodent avec | entre eux et , entre les scores");

        int [][] scores = Protocole.decoderScores(Protocole.donnees(ligne));

        Test.test(scores.length == 3, "On retrouve 3 scores");
        Test.test(scores[0].length == 2, "Le socre de J1 contient son total et le total de la pile en cours");
        Test.test(scores[0][0] == 10 && scores[0][1] == 2, "Le score 0 contient 10 points au total et 2 points au niveau de la pile");
    }

    private void testAllerRetourJoueur() {
        Jeu jeu = new Jeu(3);
        Joueur j1 = new Joueur("j1", false);
        Joueur j2 = new Joueur("j2", true, TypeBot.FACILE);
        Joueur j3 = new Joueur("j3", true, TypeBot.FACILE);
        jeu.ajouterJoueur(j1);
        jeu.ajouterJoueur(j2);
        jeu.ajouterJoueur(j3);

        String ligne = Protocole.encoderJoueurs(jeu);
        Test.test(ligne.equals("JOUEURS j1;j2;j3"), "Les joueurs s'encodent avec ; entre chaque joueur");

        String [] joueurs = Protocole.decoderJoueurs(Protocole.donnees(ligne));

        Test.test(joueurs.length == 3, "On retrouve 3 joueurs");
        Test.test(joueurs[0].equals("j1"), "Le joueur 1 s'appelle j1");
    }
}
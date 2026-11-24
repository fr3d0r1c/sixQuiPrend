package test;

import client.EtatClient;

public class TestEtatClient {

    public static void main(String[] args) {
        TestEtatClient monTest = new TestEtatClient();
        monTest.testDebutDePartie();
        monTest.testDeuxiemeMain();
        monTest.testChoixDeSerie();
    }
    
    private void testDebutDePartie() {
        EtatClient etat = new EtatClient();

        etat.traiter("BIENVENUE 1");
        etat.traiter("JOUEURS Fred;Alice;Bot 1");
        etat.traiter("SERIES 10|22,30|45|90");
        etat.traiter("MAIN 3 15 27");
        etat.traiter("A_TOI_DE_JOUER");

        Test.test(etat.getMaPosition() == 1, "ma position est celle du BIENVENUE");
        Test.test(etat.getNoms()[1].equals("Alice"), "le joueur 1 s'appelle Alice");
        Test.test(etat.getSerie(1).nombreDeCartes() == 2, "la série 1 a 2 cartes");
        Test.test(etat.getMain().nombreDeCartes() == 3, "la main a 3 cartes");
        Test.test(etat.getMain().getCarte(0).getValeur() == 3, "la 1re carte de la main est le 3");
        Test.test(etat.getSerie(1).getCarte(1).getValeur() == 30, "la 2e carte de la série 1 est le 30");
        Test.test(etat.estMonTour(), "c'est à moi de jouer");
    }

    private void testDeuxiemeMain() {
        EtatClient etat = new EtatClient();
        etat.traiter("MAIN 3 15 27");
        etat.traiter("MAIN 40 50"); 

        Test.test(etat.getMain().nombreDeCartes() == 2, "la 2e main remplace la 1re");
        Test.test(etat.getMain().getCarte(0).getValeur() == 40, "la main commence par le 40");
    }

    private void testChoixDeSerie() {
        EtatClient etat = new EtatClient();

        // c'est moi qui dois choisir
        etat.traiter("CHOISIS_SERIE");
        Test.test(etat.doitChoisirSerie(), "après CHOISIS_SERIE, je dois choisir");
        etat.aChoisiUneSerie();
        Test.test(!etat.doitChoisirSerie(), "après mon choix, je n'ai plus à choisir");

        // c'est un autre joueur qui choisit
        etat.traiter("ATTENTE_SERIE 2");
        Test.test(etat.getQuiChoisit() == 2, "le joueur 2 est en train de choisir");
        etat.traiter("A_TOI_DE_JOUER");
        Test.test(etat.getQuiChoisit() == -1, "au tour suivant, plus personne ne choisit");
    }
}

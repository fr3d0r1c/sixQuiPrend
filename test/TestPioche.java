package test;

import modele.Pioche;
import modele.Carte;

public class TestPioche {
	public static void main(String[] args) {
		TestPioche monTest = new TestPioche();
		monTest.testConstructeur1();
		monTest.testConstructeur2();
		monTest.testConstructeur3();
		monTest.testConstructeur4();
		monTest.testConstructeur5();
		monTest.testConstructeur6();
	}
	private void testConstructeur1() {
		Pioche p = new Pioche();
		System.out.println(p);
	}
	private void testConstructeur2() {
		Pioche p = new Pioche();
		boolean [] trouve = new boolean[105];
		System.out.println(p);
		for(int i=0; i<105; i++)
			trouve[i] = false;
		for(int i=0; i<104; i++)
			trouve[p.getCarte(i).getValeur()] = true;
		boolean result = true;
		int i=1;
		while(i<=104 && result)
			result&=trouve[i++];
		Test.test(result, "test de la pr�sence de toutes les cartes");
	}
	private void testConstructeur3() {
		Pioche p = new Pioche();
		Test.test(p.nombreCartesDisponibles()==104, "test du nombre initial de cartes disponibles");
		Test.test(p.estDisponible(0), "test de la disponibilit� de la premi�re carte de la pioche");
		Test.test(p.estDisponible(-1)==false && p.estDisponible(104)==false,
				"test du param�tre de \"estDisponible\"");
	}	
	private void testConstructeur4() {
		Pioche p = new Pioche();
		Carte c1 = p.getCarte(0);
		Carte c2 = p.getCarte(1);
		Carte c3 = p.piocher();
		Carte c4 = p.piocher();
		Test.test(c1==c3 && !p.estDisponible(0), "test de la pioche du premier �l�ment");
		Test.test(c2==c4 && !p.estDisponible(1), "test de la pioche du deuxi�me �l�ment");
	}
	private void testConstructeur5() {
		Pioche p = new Pioche();
		for(int i=0; i<104; i++)
			p.piocher();
		Test.test(p.piocher()==null, "test de piocher toutes les cartes");		
	}
	private void testConstructeur6() {
		Pioche p = new Pioche();
		for(int i=0; i<104; i++)
			p.piocher();
		p.reinitialiser();
		Test.test(p.piocher()==p.getCarte(0), "test de la r�initialisation de la pioche");	
	}
}

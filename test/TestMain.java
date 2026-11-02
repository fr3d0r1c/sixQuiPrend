package test;

import modele.Main;
import modele.Carte;

public class TestMain {
	public static void main(String[] args) {
		TestMain monTest = new TestMain();
		monTest.testConstructeur1();
		monTest.testConstructeur2();
		monTest.testConstructeur3();
		monTest.testConstructeur4();
		monTest.testConstructeur5();
		monTest.testConstructeur6();
	}
	private void testConstructeur1() {
		Main m = new Main();
		Test.test(m.nombreDeCartes()==0, "test de la cr�ation d'une main");
	}
	private void testConstructeur2() {
		Main m = new Main();
		m.ajouterCarte(new Carte(10));
		Test.test(m.nombreDeCartes()==1, "test de l'ajout d'une carte");
	}
	private void testConstructeur3() {
		Main m = new Main();
		m.ajouterCarte(new Carte(10));
		m.ajouterCarte(new Carte(11));
		m.ajouterCarte(new Carte(12));
		Test.test(m.toString().compareTo("[(10,3),(11,5),(12,1)]")==0, 
				"test de l'affichage d'une main");
	}
	private void testConstructeur4() {
		Main m = new Main();
		Carte c1 = new Carte(10);
		m.ajouterCarte(c1);
		m.ajouterCarte(new Carte(11));
		m.ajouterCarte(new Carte(12));
		Carte c2 = m.prendreUneCarte(0);
		Test.test(c1==c2, "test pour prendre la premi�re carte de la main");
		Test.test(m.toString().compareTo("[(11,5),(12,1)]")==0, 
				"test du reste de la main apr�s avoir pris la premi�re carte de la main");
	}
	private void testConstructeur5() {
		Main m = new Main();
		Carte c1 = new Carte(11);
		m.ajouterCarte(new Carte(10));
		m.ajouterCarte(c1);
		m.ajouterCarte(new Carte(12));
		Carte c2 = m.prendreUneCarte(1);
		Test.test(c1==c2, "test pour prendre la deuxi�me carte de la main");
		Test.test(m.toString().compareTo("[(10,3),(12,1)]")==0, 
				"test du reste de la main apr�s avoir pris la deuxi�me carte de la main");
	}
	private void testConstructeur6() {
		Main m = new Main();
		Carte c1 = new Carte(12);
		m.ajouterCarte(new Carte(10));
		m.ajouterCarte(new Carte(11));
		m.ajouterCarte(c1);
		Carte c2 = m.prendreUneCarte(2);
		Test.test(c1==c2, "test pour prendre la troisi�me carte de la main");
		Test.test(m.toString().compareTo("[(10,3),(11,5)]")==0, 
				"test du reste de la main apr�s avoir pris la deuxi�me carte de la main");
	}
}

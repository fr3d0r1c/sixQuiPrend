package test;

import modele.Serie;
import modele.Carte;

public class TestSerie {
	public static void main(String[] args) {
		TestSerie monTest = new TestSerie();
		monTest.testConstructeur1();
		//monTest.testConstructeur2();
		//monTest.testConstructeur3();
		//monTest.testConstructeur4();
		//monTest.testConstructeur5();
	}
	private void testConstructeur1() {
		Serie s = new Serie();
		Test.test(s.nombreDeCartes()==0, "test de la création d'une série");
	}
	private void testConstructeur2() {
		Serie s = new Serie();
		s.ajouterCarte(new Carte(10));
		Test.test(s.nombreDeCartes()==1, "test de l'ajout d'une carte");
	}
	private void testConstructeur3() {
		Serie s = new Serie();
		s.ajouterCarte(new Carte(10));
		s.ajouterCarte(new Carte(11));
		s.ajouterCarte(new Carte(12));
		Test.test(s.toString().compareTo("[(10,3),(11,5),(12,1)]")==0, 
				"test de l'affichage d'une série");
	}
	private void testConstructeur4() {
		Serie s = new Serie();
		Carte c1 = new Carte(10);
		Carte c2 = new Carte(11);
		Carte c3 = new Carte(12);
		s.ajouterCarte(c1);
		s.ajouterCarte(c2);
		s.ajouterCarte(c3);
		Test.test(c1==s.getCarte(0), "test de la fonction getCarte");
		Test.test(c3==s.derniereCarte(), "test de la fonction derniereCarte");

	}
	private void testConstructeur5() {
		Serie s = new Serie();
		Carte c1 = new Carte(10);
		Carte c2 = new Carte(11);
		Carte c3 = new Carte(12);
		s.ajouterCarte(c1);
		s.ajouterCarte(c2);
		s.ajouterCarte(c3);
		s.vider();
		Test.test(s.nombreDeCartes()==0, "test de la fonction vider");
	}
}

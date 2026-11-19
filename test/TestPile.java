package test;

import modele.Pile;
import modele.Carte;

public class TestPile {
	public static void main(String[] args) {
		TestPile monTest = new TestPile();
		monTest.testConstructeur1();
		monTest.testConstructeur2();
		monTest.testConstructeur3();
		monTest.testNombreDeCartes();
	}
	private void testConstructeur1() {
		Pile p = new Pile();
		p.ajouterCarte(new Carte(10));
		p.ajouterCarte(new Carte(11));
		p.ajouterCarte(new Carte(12));
		Test.test(p.calculPoints()==9, "test du calcul des points dans la pile");
	}
	private void testConstructeur2() {
		Pile p = new Pile();
		p.ajouterCarte(new Carte(10));
		p.ajouterCarte(new Carte(11));
		p.ajouterCarte(new Carte(12));
		p.vider();
		Test.test(p.calculPoints()==0, "test pour vider la pile");
	}
	private void testConstructeur3() {
		Pile p = new Pile();
		p.ajouterCarte(new Carte(10));
		p.ajouterCarte(new Carte(11));
		p.ajouterCarte(new Carte(12));
		Test.test(p.toString().compareTo("{(10,3),(11,5),(12,1)}")==0, "test de la m�thode\"toString\"");
	}

	private void testNombreDeCartes() {
		// PRÉPARER
		Pile pile = new Pile();

		Test.test(pile.nombreDeCartes() == 0, "une pile neuve ne contient aucune carte");

		pile.ajouterCarte(new Carte(55));
		pile.ajouterCarte(new Carte(10));

		Test.test(pile.nombreDeCartes() == 2, "la pile contient 2 cartes (et non 10 têtes)");

	}
}

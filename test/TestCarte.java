package test;

import modele.Carte;

public class TestCarte {
	public static void main(String[] args) {
		TestCarte monTest = new TestCarte();
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
		Carte c = new Carte(1);
		Test.test(c.getValeur()==1, "test num�ro de la carte 1");
		Test.test(c.getNombreTetesDeBoeuf()==1, "test nombre de t�tes de boeuf de la carte 1");
	}
	private void testConstructeur2() {
		Carte c = new Carte(104);
		Test.test(c.getValeur()==104, "test num�ro de la carte 104");
		Test.test(c.getNombreTetesDeBoeuf()==1, "test nombre de t�tes de boeuf de la carte 104");
	}
	private void testConstructeur3() {
		Carte c = new Carte(13);
		Test.test(c.getValeur()==13, "test num�ro de la carte 13");
		Test.test(c.getNombreTetesDeBoeuf()==1, "test nombre de t�tes de boeuf de la carte 13");
	}
	private void testConstructeur4() {
		Carte c = new Carte(0);
		Test.test(c.getValeur()==0, "test num�ro carte 0 (impossible)");
		Test.test(c.getNombreTetesDeBoeuf()==0, "test nombre de t�tes de boeuf carte 0 (impossible)");
	}
	private void testConstructeur5() {
		Carte c = new Carte(105);
		Test.test(c.getValeur()==0, "test num�ro carte 105 (impossible)");
		Test.test(c.getNombreTetesDeBoeuf()==0, "test nombre de t�tes de boeuf carte 105 (impossible)");
	}
	private void testConstructeur6() {
		Carte c = new Carte(90);
		Test.test(c.getValeur()==90, "test num�ro carte 90");
		Test.test(c.getNombreTetesDeBoeuf()==3, "test nombre de t�tes de boeuf carte 90");
	}
	private void testConstructeur7() {
		Carte c = new Carte(85);
		Test.test(c.getValeur()==85, "test num�ro carte 85");
		Test.test(c.getNombreTetesDeBoeuf()==2, "test nombre de t�tes de boeuf carte 85");
	}	
	private void testConstructeur8() {
		Carte c = new Carte(55);
		Test.test(c.getValeur()==55, "test num�ro carte 55");
		Test.test(c.getNombreTetesDeBoeuf()==7, "test nombre de t�tes de boeuf carte 55");
	}
	private void testConstructeur9() {
		Carte c = new Carte(11);
		Test.test(c.getValeur()==11, "test num�ro carte 11");
		Test.test(c.getNombreTetesDeBoeuf()==5, "test nombre de t�tes de boeuf carte 11");
	}
	private void testConstructeur10() {
		Carte c = new Carte(77);
		Test.test(c.toString().compareTo("(77,5)")==0, "test de l'affichage de la carte 77");
	}
}

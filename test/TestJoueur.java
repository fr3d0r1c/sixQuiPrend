package test;

import modele.Joueur;

public class TestJoueur {
	public static void main(String[] args) {
		TestJoueur monTest = new TestJoueur();
		monTest.testConstructeur1();
		monTest.testConstructeur2();
		monTest.testConstructeur3();
	}
	private void testConstructeur1() {
		Joueur j = new Joueur("Arthur", false);
		Test.test(j.getNom().compareTo("Arthur")==0, "test du nom");
		Test.test(j.getPile()!=null, "test de la pile");
		Test.test(j.getMain()!=null, "test de la main");
		Test.test(j.estAvatar()==false, "test du champ \"avatar\"");
		Test.test(j.getNombreDePoints()==0, "test du nombre initial de points");
	}
	private void testConstructeur2() {
		Joueur j = new Joueur("Joueur 1", true);
		Test.test(j.estAvatar()==true, "test du champ \"avatar\"");
	}
	private void testConstructeur3() {
		Joueur j = new Joueur("Joueur 1", true);
		j.ajouterPoints(5);
		Test.test(j.getNombreDePoints()==5, "test de l'ajout de points");
	}
}

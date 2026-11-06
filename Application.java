import java.util.Random;
import java.util.Scanner;

import modele.*;

public class Application {
	private Jeu jeu;
	private int nbJoueurs;
	private Scanner clavier;
	
	private void initialisationPartie() {
		System.out.print("Combien de joueur (2 à 10) ? ");
		nbJoueurs = clavier.nextInt();
		while (nbJoueurs < 2 || nbJoueurs > 10) {
			System.out.print("Erreur : Nombre de joueurs invalide. Réessayez : ");
			nbJoueurs = clavier.nextInt();
		}
		clavier.nextLine();

		jeu = new Jeu(nbJoueurs);

		System.out.print("Bonjour, qui es-tu ? ");
		String nom = clavier.nextLine();

		Joueur joueur = new Joueur(nom, false);
		jeu.ajouterJoueur(joueur);

		for (int i = 1; i < nbJoueurs; i++) {
			jeu.ajouterJoueur(new Joueur("Bot" + i, true));
		}
	}
	
	public void jouer() {
		clavier = new Scanner(System.in);
		initialisationPartie();
		do {
			jouerUneManche();
			afficherScores();
		} while (!partieFinie());
		System.out.println("LA PARTIE EST FINIE");
		clavier.close(); 	
	}
	
	private void jouerUneManche() {
		// on affiche les séries
		// les joueurs choisissent une carte
		// (les avatars prendront leur carte aléatoirement)
		// les joueurs jouent chaun leur tour, en fonction de la valeur des cartes jouées

	}

	private void calculerLesPoints() {
		// pour chaque joueur, on regarde les points des piles et on ajoute
		for (int i=0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			Pile pile = joueur.getPile();

			int pointsPile = pile.calculPoints();
			joueur.ajouterPoints(pointsPile);
			pile.vider();
		}
	}

	private void initialiserManche() {
		Pioche pioche = jeu.getPioche();
		
		pioche.reinitialiser();
		for (int i = 0; i < 4; i++) {
			Serie serie = jeu.getSerie(i);
			Carte carte = pioche.piocher();

			serie.vider();
			serie.ajouterCarte(carte);
		}

		for (int i = 0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			
			for (int j = 0; j < 10; j++) {
				Main main = joueur.getMain();
				Carte carte = pioche.piocher();
				main.ajouterCarte(carte);
			}
		}
	}

	private boolean partieFinie() {
		for (int i = 0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			int nbPoints = joueur.getNombreDePoints();
			if (nbPoints > 66) {
				return true;
			}
		}
		return false;
	}
	
	private void afficherScores() {
		for (int i = 0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			String nom = joueur.getNom();
			int nbPoints = joueur.getNombreDePoints();
			System.out.println(nom + " : " + nbPoints + " points");
		}
	}
	
	private Carte choisirCarte(Joueur j) {
		Main main = j.getMain();
		int nombreCartes = main.nombreDeCartes();
		Carte carteChoisie;
		Random r = new Random();

		if (j.estAvatar()) {
			int numCarte = r.nextInt(nombreCartes);
			carteChoisie = main.prendreUneCarte(numCarte);
		} else {
			System.out.println(main);
			System.out.print("Choisissez une carte : ");
			int choix = clavier.nextInt();
			while (choix < 0 || choix >= nombreCartes) {
				System.out.print("Choix invalide. Réessayez : ");
				choix = clavier.nextInt();
			}
			carteChoisie = main.prendreUneCarte(choix);
		}

		return carteChoisie;
		
	}
	
	private int choisirSerie(Joueur j) {
		Random r = new Random();
		int choix;

		if (j.estAvatar()) {
			choix = r.nextInt(4);
		} else {
			for (int i = 0; i < 4; i++) {
				System.out.println(i + " : " + jeu.getSerie(i));
			}
			
			System.out.print("Choisissez une série : ");
			choix = clavier.nextInt();

			while (choix < 0 || choix >= 4) {
				System.out.print("Choix invalide. Réessayez : ");
				choix = clavier.nextInt();
			}
		}

		return choix;
	}
	
	private void deposerCarte(Carte c, Joueur j) {
		// on déposer sur l'un des séries la carte 'c' du joueur 'j'
	}
	
	private void prendreToutesLesCartesDeLaSerie(Joueur j, int choix) {
		// on met toutes les cartes de la série choisie dans
		// la pile du joueur 'j'
	}

	public static void main(String[] args) {
		Application monAppli = new Application();
		monAppli.jouer();		
	}
}
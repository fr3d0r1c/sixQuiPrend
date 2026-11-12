import java.util.Random;
import java.util.Scanner;

import modele.*;

public class Application {
	private Jeu jeu;
	private int nbJoueurs;
	private Scanner clavier;
	Random r = new Random();
	
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

		initialiserManche();
		Carte[] cartesChoisies = new Carte[nbJoueurs];
		Joueur[] joueurs = new Joueur[nbJoueurs];

		while(jeu.getJoueur(0).getMain().nombreDeCartes() > 0) {
			for (int i = 0; i < nbJoueurs; i++) {
				joueurs[i] = jeu.getJoueur(i);
				cartesChoisies[i] = choisirCarte(joueurs[i]);
			}

			System.out.println("=== CARTES CHOISIES ===");

			for (int i = 0; i < nbJoueurs; i++) {
				System.out.println(
					joueurs[i].getNom()
					+ " joue "
					+ cartesChoisies[i]
				);
			}

			for (int i = 0; i < nbJoueurs - 1; i++) {
				for (int j = i + 1; j < nbJoueurs; j++) {

						if (cartesChoisies[i].getValeur() > cartesChoisies[j].getValeur()) {
							Carte tempCarte = cartesChoisies[i];
            				cartesChoisies[i] = cartesChoisies[j];
							cartesChoisies[j] = tempCarte;

							Joueur tempJoueur = joueurs[i];
							joueurs[i] = joueurs[j];
            				joueurs[j] = tempJoueur;
						}

				}
			}

			System.out.println("=== ORDRE DE JEU ===");

			for (int i = 0; i < nbJoueurs; i++) {
				System.out.println(
					joueurs[i].getNom()
					+ " -> "
					+ cartesChoisies[i]
				);
			}

			for (int i = 0; i < nbJoueurs; i++) {
				deposerCarte(cartesChoisies[i], joueurs[i]);
				
				for (int s = 0; s < 4; s++) {
					System.out.println(
						"Serie " + (s + 1) + " : "
						+ jeu.getSerie(s)
					);
				}
			}
		}

		System.out.println("=== FIN DE MANCHE ===");

		calculerLesPoints();

	}

	private void calculerLesPoints() {
		// pour chaque joueur, on regarde les points des piles et on ajoute
		for (int i=0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			Pile pile = joueur.getPile();

			int pointsPile = pile.calculPoints();
			joueur.ajouterPoints(pointsPile);
			pile.vider();

			System.out.println(
				joueur.getNom()
				+ " recupere "
				+ pointsPile
				+ " points"
			);

			System.out.println(
				joueur.getNom()
				+ " total : "
				+ joueur.getNombreDePoints()
			);
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

		System.out.println("=== DEBUT DE MANCHE ===");

		for (int i = 0; i < 4; i++) {
			System.out.println(
				"Serie " + (i+1) + " : " + jeu.getSerie(i)
			);
		}

		System.out.println();

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
		
		if (nombreCartes == 1) {
			return main.prendreUneCarte(0);
		}

		Carte carteChoisie;

		if (j.estAvatar()) {
			int numCarte = r.nextInt(nombreCartes);
			carteChoisie = main.prendreUneCarte(numCarte);
		} else {
			System.out.println(main);
			System.out.print("Choisissez une carte : ");
			int choix = clavier.nextInt();
			while (choix < 1 || choix > nombreCartes) {
				System.out.print("Choix invalide. Réessayez : ");
				choix = clavier.nextInt();
			}
			carteChoisie = main.prendreUneCarte(choix - 1);
		}

		return carteChoisie;
		
	}
	
	private int choisirSerie(Joueur j) {
		int choix;

		if (j.estAvatar()) {
			return r.nextInt(4);
		} else {

			for (int i = 0; i < 4; i++) {
				System.out.println((i + 1) + " : " + jeu.getSerie(i));
			}

			System.out.print("Choisissez une série : ");
			choix = clavier.nextInt();

			while (choix < 1 || choix > 4) {
				System.out.print("Choix invalide. Réessayez : ");
				choix = clavier.nextInt();
			}

			choix = choix - 1;
		}

		return choix;
	}


	private void deposerCarte(Carte c, Joueur j) {
		System.out.println(
			j.getNom()
			+ " depose "
			+ c
		);
		// on déposer sur l'un des séries la carte 'c' du joueur 'j'
		int meilleureSerie = -1;
		int meilleureValeur = -1;

		for (int i = 0; i < 4; i++) {
			int valeurSerie = jeu.getSerie(i).derniereCarte().getValeur();

			if (valeurSerie < c.getValeur()) {
				if (valeurSerie > meilleureValeur) {
					meilleureValeur = valeurSerie;
					meilleureSerie = i;
				}
			}
		}

		if (meilleureSerie == -1) {
			int choix = choisirSerie(j);
			prendreToutesLesCartesDeLaSerie(j, choix);
			jeu.getSerie(choix).ajouterCarte(c);
			System.out.println(
				j.getNom()
				+ " prend la serie "
				+ (choix + 1)
			);
		} else {
			Serie serie = jeu.getSerie(meilleureSerie);
			if (serie.nombreDeCartes() == 5) {
				prendreToutesLesCartesDeLaSerie(j, meilleureSerie);
				serie.ajouterCarte(c);
				System.out.println(
					j.getNom()
					+ " prend les 5 cartes de la serie "
					+ (meilleureSerie + 1)
				);
			} else {
				serie.ajouterCarte(c);
				System.out.println(
					c + " ajoutee a la serie "
					+ (meilleureSerie + 1)
				);
			}
		}

	}
	
	private void prendreToutesLesCartesDeLaSerie(Joueur j, int choix) {
		// on met toutes les cartes de la série choisie dans
		// la pile du joueur 'j'

		Serie serie = jeu.getSerie(choix);
		Pile pile = j.getPile();

		for (int i = 0; i < serie.nombreDeCartes(); i++) {
			pile.ajouterCarte(serie.getCarte(i));
		}

		serie.vider();
	}

	public static void main(String[] args) {
		Application monAppli = new Application();
		monAppli.jouer();		
	}
}
import java.util.Random;
import java.util.Scanner;

import modele.*;

public class Application {
	private Jeu jeu;
	private int nbJoueurs;
	private Scanner clavier;
	private Random r = new Random();
	private static final String UNITE_TETE = "tête";
	private static final String UNITE_POINT = "pt";
	private static final String UNITE_CARTE = "carte";

	private void initialisationPartie() {
		System.out.print("Combien de joueur (2 à 10) ? ");
		nbJoueurs = lireEntier(2, 10);

		jeu = new Jeu(nbJoueurs);

		System.out.print("Bonjour, qui es-tu ? ");
		String nom = clavier.nextLine();

		Joueur joueur = new Joueur(nom, false, null);
		jeu.ajouterJoueur(joueur);

		for (int i = 1; i < nbJoueurs; i++) {
			jeu.ajouterJoueur(new Joueur("Bot" + i, true, TypeBot.ALEATOIRE));
		}

		affihcerRegles();
	}
	
	public void jouer() {
		clavier = new Scanner(System.in);
		initialisationPartie();
		do {
			jouerUneManche();
			afficherScores();
		} while (!partieFinie());
		System.out.println("LA PARTIE EST FINIE");
		afficherGagnant();
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
				
				afficherPlateau();
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
				+ " récupère "
				+ formaterQuantite(pointsPile, UNITE_POINT)
			);

			System.out.println(
				joueur.getNom()
				+ " total : "
				+ formaterQuantite(joueur.getNombreDePoints(), UNITE_POINT)
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

		System.out.println();
		System.out.println("=========================================");
		System.out.println("           DEBUT DE MANCHE");
		System.out.println("=========================================");

		afficherPlateau();

		for (int i = 0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			
			for (int j = 0; j < 10; j++) {
				Main main = joueur.getMain();
				Carte carte = pioche.piocher();
				main.ajouterCarte(carte);
			}

			joueur.getMain().trierMain();
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
			System.out.println(nom + " : " + formaterQuantite(nbPoints, UNITE_POINT));
		}
	}
	
	private Carte choisirCarte(Joueur j) {
		Main main = j.getMain();
		int nombreCartes = main.nombreDeCartes();
		Carte carteChoisie;

		// Dernière carte : aucun choix possible, on la joue directement
		if (nombreCartes == 1) {
			return main.prendreUneCarte(0);
		}

		if (j.estAvatar()) {
			switch (j.getTypeBot()) {
				case ALEATOIRE:
					int numCarte = r.nextInt(nombreCartes);
					carteChoisie = main.prendreUneCarte(numCarte);
					break;

				case FACILE:
					carteChoisie = choisirCarteBotFacile(j);
					break;

				default:
					throw new IllegalStateException("Type de bot non géré : " + j.getTypeBot());
			}
		} else {
			afficherMain(j);
			System.out.print("Choisissez une carte : ");
			int choix = lireEntier(1, nombreCartes);
			carteChoisie = main.prendreUneCarte(choix - 1);
		}

		return carteChoisie;

	}

	private int choisirSerie(Joueur j) {
		int choix;

		if (j.estAvatar()) {
			choix = r.nextInt(4);
		} else {

			System.out.println(
				"Ta carte est plus petite que toutes les séries : tu dois en ramasser une. "
			);

			afficherSeries();

			System.out.print("Choisissez une série (1-4) : ");
			choix = lireEntier(1, 4);

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
			int points = jeu.getSerie(choix).calculPoints();
			prendreToutesLesCartesDeLaSerie(j, choix);
			jeu.getSerie(choix).ajouterCarte(c);
			System.out.println(
				j.getNom()
				+ " prend la serie "
				+ (choix + 1)
				+ " (+"
				+ formaterQuantite(points, UNITE_TETE)
				+ ")"
			);
		} else {
			Serie serie = jeu.getSerie(meilleureSerie);
			if (serie.nombreDeCartes() == 5) {
				int points = serie.calculPoints();
				prendreToutesLesCartesDeLaSerie(j, meilleureSerie);
				serie.ajouterCarte(c);
				System.out.println(
					j.getNom()
					+ " prend les 5 cartes de la serie "
					+ (meilleureSerie + 1)
					+ " (+"
					+ formaterQuantite(points, UNITE_TETE)
					+ ")"
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

	private void afficherMain(Joueur j) {
		Main main = j.getMain();

		System.out.println("====================================");
		System.out.println("MAIN DE " + j.getNom().toUpperCase());
		System.out.println("====================================");

		int nombreCartes = main.nombreDeCartes();

		for (int i = 0; i < nombreCartes; i++) {
			System.out.print("    " + (i + 1) + "         ");
		}
		System.out.println();

		String[][] cartes = new String[nombreCartes][];

		for (int i = 0; i < nombreCartes; i++) {
			cartes[i] = main.getCarte(i).toAsciiLines();
		}

		for (int ligne = 0; ligne < 4; ligne++) {

			for (int carte = 0; carte < nombreCartes; carte++) {
				System.out.print(cartes[carte][ligne] + " ");
			}

			System.out.println();
		}
	}

	private void afficherSeries() {
		System.out.println("=========================================");
		System.out.println("                 PLATEAU");
		System.out.println("=========================================");

		Serie[] series = new Serie[4];

		for (int i = 0; i < 4; i++) {
			series[i] = jeu.getSerie(i);
		} 

		for (int i = 0; i < 4; i++) {
			System.out.printf("     Serie %-2d        ", i + 1);
		}
		System.out.println();

		// Dernière carte de chaque série
		String[][] cartes = new String[4][];

		for (int i = 0; i < 4; i++) {
			cartes[i] = series[i]
				.derniereCarte()
				.toAsciiLines();
		}

		for (int ligne = 0; ligne < 4; ligne++) {

			for (int serie = 0; serie < 4; serie++) {
				System.out.print(cartes[serie][ligne] + "   ");
			}

			System.out.println();
		}

		System.out.println();

		// Infos complémentaires
		for (int i = 0; i < 4; i++) {
			System.out.printf(
				"[%s | %s]    ",
				formaterQuantite(series[i].nombreDeCartes(), UNITE_CARTE),
				formaterQuantite(series[i].calculPoints(), UNITE_TETE)
			);
		}

		System.out.println();

		System.out.println("====================================");

	}

	private void afficherPlateau() {

		System.out.println();
		System.out.println("=========================================");
		System.out.println("                 PLATEAU");
		System.out.println("=========================================");

		Serie[] series = new Serie[4];
		String[][] cartes = new String[4][];

		for (int i = 0; i < 4; i++) {
			series[i] = jeu.getSerie(i);
			cartes[i] = series[i]
					.derniereCarte()
					.toAsciiLines();
		}

		// Titres
		for (int i = 0; i < 4; i++) {
			System.out.printf("     Serie %-2d        ", i + 1);
		}
		System.out.println();

		// Cartes ASCII
		for (int ligne = 0; ligne < 4; ligne++) {

			for (int serie = 0; serie < 4; serie++) {
				System.out.print(
					cartes[serie][ligne]
					+ "   "
				);
			}

			System.out.println();
		}

		System.out.println();

		// Nombre de cartes
		for (int i = 0; i < 4; i++) {
			System.out.printf(
				"[%s]     ",
				formaterQuantite(series[i].nombreDeCartes(), UNITE_CARTE)
			);
		}

		System.out.println();

		// Nombre de points
		for (int i = 0; i < 4; i++) {
			System.out.printf(
				"[%s]     ",
				formaterQuantite(series[i].calculPoints(), UNITE_TETE)
			);
		}

		System.out.println();
		System.out.println();

		// Contenu complet de chaque série
		for (int i = 0; i < 4; i++) {
			System.out.println(
				"Serie "
				+ (i + 1)
				+ " : "
				+ series[i]
			);
		}

		System.out.println("=========================================");
	}

	private Carte choisirCarteBotFacile(Joueur j) {
		return j.getMain().prendreUneCarte(0);
	}

	private void afficherGagnant() {
		Joueur meilleurJoueur = jeu.getJoueur(0);
		int ptsWinner = meilleurJoueur.getNombreDePoints();

		for (int i = 1; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			int nbPoints = joueur.getNombreDePoints();
			if (nbPoints < ptsWinner) {
				ptsWinner = nbPoints;
				meilleurJoueur = joueur;
			}
		}

		System.out.println("Le gagnant est " + meilleurJoueur.getNom() + " avec " + formaterQuantite(ptsWinner, UNITE_POINT));
	}

	private int lireEntier(int min, int max) {
		while (true) {
			String ligne = clavier.nextLine().trim();
			try {
				int nombre = Integer.parseInt(ligne);
				if (nombre >= min && nombre <= max) {
					return nombre;
				}
			} catch (NumberFormatException e) {
				// Ignore et réessayer
			}

			System.out.print("Choix invalide (" + min + " à " + max + "). Réessayez : ");
		}
	}

	private void affihcerRegles() {
		Joueur joueur = jeu.getJoueur(0);

		System.out.println("Bienvenue " + joueur.getNom() + " !");

		System.out.println(
			"But du jeu : ramasser le moins de têtes de boeufs possible"
		);

		System.out.println(
			"(23,1) -> Carte numéro 23, 1 tête de boeuf"
		);

		System.out.println(
			"La partie se termine dès qu'un joueur dépasse 66 têtes."
		);

		System.out.println();
	}

	private String formaterQuantite(int nombre, String mot) {
		return nombre + " " + mot + (nombre > 1 ? "s" : "");
	}
	public static void main(String[] args) {
		Application monAppli = new Application();
		monAppli.jouer();		
	}
}
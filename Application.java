import java.util.Scanner;

import modele.*;

public class Application {
	private Jeu jeu;
	private int nbJoueurs;
	private Scanner clavier;
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

		afficherRegles();
	}
	
	public void jouer() {
		clavier = new Scanner(System.in);
		initialisationPartie();
		do {
			jouerUneManche();
			afficherScores();
		} while (!jeu.estFinie());
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
		CarteJouee[] cartesJouees = new CarteJouee[nbJoueurs];

		while(jeu.getJoueur(0).getMain().nombreDeCartes() > 0) {
			for (int i = 0; i < nbJoueurs; i++) {
				Joueur joueur = jeu.getJoueur(i);
				cartesJouees[i] = new CarteJouee(choisirCarte(joueur), joueur);
			}

			System.out.println("=== CARTES CHOISIES ===");

			for (int i = 0; i < nbJoueurs; i++) {
				System.out.println(
					cartesJouees[i].getJoueur().getNom()
					+ " joue "
					+ cartesJouees[i].getCarte()
				);
			}

			jeu.ordonnerCartesJouees(cartesJouees);

			System.out.println("=== ORDRE DE JEU ===");

			for (int i = 0; i < nbJoueurs; i++) {
				System.out.println(
					cartesJouees[i].getJoueur().getNom()
					+ " -> "
					+ cartesJouees[i].getCarte()
				);
			}

			for (int i = 0; i < nbJoueurs; i++) {
				deposerCarte(cartesJouees[i].getCarte(), cartesJouees[i].getJoueur());
			}

			afficherPlateau();
		}

		System.out.println("=== FIN DE MANCHE ===");

		calculerLesPoints();

	}

	private void calculerLesPoints() {
		int[] scoreAvant = new int[nbJoueurs];
		for(int i=0; i < scoreAvant.length; i++) {
			Joueur j = jeu.getJoueur(i);
			scoreAvant[i] = j.getNombreDePoints();
		}
		jeu.terminerManche();

		for (int i=0; i < nbJoueurs; i++) {
			Joueur joueur = jeu.getJoueur(i);
			System.out.println(
				joueur.getNom()
				+ " récupère "
				+ formaterQuantite(joueur.getNombreDePoints() - scoreAvant[i], UNITE_POINT)
			);

			System.out.println(
				joueur.getNom()
				+ " total : "
				+ formaterQuantite(joueur.getNombreDePoints(), UNITE_POINT)
			);
		}
	}

	private void initialiserManche() {
		jeu.preparerManche();

		System.out.println();
		System.out.println("=========================================");
		System.out.println("           DEBUT DE MANCHE");
		System.out.println("=========================================");

		afficherPlateau();
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
			carteChoisie = jeu.choisirCarteBot(j);
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
			choix = jeu.choisirSerieBot(j);
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
		int meilleureSerie = jeu.trouverSerie(c);
		
		System.out.println(
			j.getNom()
			+ " depose "
			+ c
		);

		if (meilleureSerie == -1) {
			int choix = choisirSerie(j);
			int points = jeu.ramasserSerie(choix, j, c);
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
				int points = jeu.ramasserSerie(meilleureSerie, j, c);
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

	private void afficherGagnant() {
		Joueur meilleurJoueur = jeu.trouverGagnant();

		System.out.println("Le gagnant est " + meilleurJoueur.getNom() + " avec " + formaterQuantite(meilleurJoueur.getNombreDePoints(), UNITE_POINT));
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

	private void afficherRegles() {
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
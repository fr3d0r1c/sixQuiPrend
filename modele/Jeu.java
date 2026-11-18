package modele;
import java.util.Random;

public class Jeu {
    private Pioche maPioche;
    private Joueur[] tableauJoueurs;
    private int nombreJoueurs;
    private Serie[] tableauSeries;
    private Random r = new Random();

    public Jeu(int nbJoueurs){
        this.maPioche = new Pioche();
        this.tableauJoueurs = new Joueur[10];
        this.tableauSeries = new Serie[4];
        this.nombreJoueurs = 0;
        for (int i=0; i<4; i++){
            tableauSeries[i] = new Serie();
        }
    }

    public Joueur getJoueur(int numero) {
        return this.tableauJoueurs[numero];
    }

    public int getNombreJoueurs() {
        return this.nombreJoueurs;
    }

    public Serie getSerie(int numero) {
        return this.tableauSeries[numero];
    }

    public void ajouterJoueur(Joueur joueur) {
        if (nombreJoueurs < 10) {
            tableauJoueurs[nombreJoueurs] = joueur;
            nombreJoueurs++;
        }
    }

    public int trouverSerie(Carte c) {
        int meilleureSerie = -1;
		int meilleureValeur = -1;

        for (int i = 0; i < 4; i++) {
            int valeurSerie = getSerie(i).derniereCarte().getValeur();

            if (valeurSerie < c.getValeur()) {
                if (valeurSerie > meilleureValeur) {
                    meilleureValeur = valeurSerie;
                    meilleureSerie = i;
                }
            }
        }

        return meilleureSerie;
    }

    public Pioche getPioche() {
        return this.maPioche;
    }

    public int ramasserSerie(int numeroSerie, Joueur j, Carte c) {
		Serie serie = getSerie(numeroSerie);
		int nombreDeTetes = serie.calculPoints();

        prendreToutesLesCartesDeLaSerie(j, numeroSerie);

        serie.ajouterCarte(c);

		return nombreDeTetes;
	}

    private void prendreToutesLesCartesDeLaSerie(Joueur j, int choix) {
		// on met toutes les cartes de la série choisie dans
		// la pile du joueur 'j'

		Serie serie = getSerie(choix);
		Pile pile = j.getPile();

		for (int i = 0; i < serie.nombreDeCartes(); i++) {
			pile.ajouterCarte(serie.getCarte(i));
		}

		serie.vider();
	}

    public boolean estFinie() {
        for (int i = 0; i < getNombreJoueurs(); i++) {
            Joueur joueur = getJoueur(i);
            int nbPoints = joueur.getNombreDePoints();
            if (nbPoints > 66) {
                return true;
            }
        }
        return false;
    }

    public Joueur trouverGagnant() {
        Joueur meilleurJoueur = getJoueur(0);
        int ptsWinner = meilleurJoueur.getNombreDePoints();

        for (int i = 1; i < getNombreJoueurs(); i++) {
            Joueur joueur = getJoueur(i);
            int nbPoints = joueur.getNombreDePoints();
            if (nbPoints < ptsWinner) {
                ptsWinner = nbPoints;
                meilleurJoueur = joueur;
            }
        }

        return meilleurJoueur;
    }

    public void ordonnerCartesJouees(CarteJouee[] cartesJouees) {
        for (int i = 0; i < cartesJouees.length - 1; i++) {
            for (int j = i + 1; j < cartesJouees.length; j++) {

                if (cartesJouees[i].getCarte().getValeur() > cartesJouees[j].getCarte().getValeur()) {
                    CarteJouee tempCarteJouee = cartesJouees[i];
                    cartesJouees[i] = cartesJouees[j];
                    cartesJouees[j] = tempCarteJouee;
                }
            }
        }
    }

    public Carte choisirCarteBot(Joueur j) {
        Carte carteChoisie;
        switch(j.getTypeBot()) {
            case ALEATOIRE:
                int numCarte = r.nextInt(j.getMain().nombreDeCartes());
                carteChoisie = j.getMain().prendreUneCarte(numCarte);
                break;
            
            case FACILE:
                carteChoisie = choisirCarteBotFacile(j);
                break;

            default:
				throw new IllegalStateException("Type de bot non géré : " + j.getTypeBot());
        }

        return carteChoisie;
    }

    public int choisirSerieBot(Joueur j) {
        return r.nextInt(4);
    }

    private Carte choisirCarteBotFacile(Joueur j) {
		return j.getMain().prendreUneCarte(0);
	}

    public void preparerManche() {
        Pioche pioche = getPioche();
        pioche.reinitialiser();

        for (int i = 0; i < 4; i++) {
            Serie serie = getSerie(i);
            Carte carte = pioche.piocher();

            serie.vider();
            serie.ajouterCarte(carte);
        }

        for (int i = 0; i < getNombreJoueurs(); i++) {
            Joueur joueur = getJoueur(i);
            Main main = joueur.getMain();

            for (int j = 0; j < 10; j++) {
                Carte carte = pioche.piocher();
                main.ajouterCarte(carte);
            }

            joueur.getMain().trierMain();
        }
    }

    // ATTENTION : même logique que deposerCarte() dans Application.
    // Toute modification de la règle de dépôt doit être faite aux deux endroits.
    public void poserCarteBot(CarteJouee carteJouee) {
        Joueur j = carteJouee.getJoueur();
        Carte c = carteJouee.getCarte();
        int meilleureSerie = trouverSerie(c);

        if (meilleureSerie == -1) {
            int choix = choisirSerieBot(j);
            ramasserSerie(choix, j, c);
        } else {
            Serie serie = getSerie(meilleureSerie);
            if (serie.nombreDeCartes() == 5) {
                ramasserSerie(meilleureSerie, j, c);
            } else {
                serie.ajouterCarte(c);
            }
        }
    }

    public void terminerManche() {
        for (int i=0; i < getNombreJoueurs(); i++) {
            Joueur joueur = getJoueur(i);
            Pile pile = joueur.getPile();

            int pointsPile = pile.calculPoints();
            joueur.ajouterPoints(pointsPile);
            pile.vider();
        }
    }

    public void jouerMancheAutomatique() {
        preparerManche();
        CarteJouee[] cartesJouees = new CarteJouee[getNombreJoueurs()];

        while (getJoueur(0).getMain().nombreDeCartes() > 0) {
            for (int i = 0; i < getNombreJoueurs(); i++) {
                Joueur joueur = getJoueur(i);
                cartesJouees[i] = new CarteJouee(choisirCarteBot(joueur), joueur);
            }

            ordonnerCartesJouees(cartesJouees);

            for (int i=0; i < getNombreJoueurs(); i++) {
                poserCarteBot(cartesJouees[i]);
            }
        }

        terminerManche();
    }

    public void jouerPartieAutomatique() {
        // ATTENTION : Les joueurs doivent être obligatoirement des bots
        do {
            jouerMancheAutomatique();
        } while(!estFinie());
    }

    public boolean estEgalite() {
        int scoreMin = trouverGagnant().getNombreDePoints();

        int nbAuScoreMin = 0;
        for(int i = 0; i < getNombreJoueurs(); i++) {
            if (getJoueur(i).getNombreDePoints() == scoreMin) {
                nbAuScoreMin++;
            }
        }

        return nbAuScoreMin > 1;
    }
}

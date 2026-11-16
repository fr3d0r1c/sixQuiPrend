package modele;

public class Jeu {
    private Pioche maPioche;
    private Joueur[] tableauJoueurs;
    private int nombreJoueurs;
    private Serie[] tableauSeries;

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

}

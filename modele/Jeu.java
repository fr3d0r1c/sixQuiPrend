package modele;
public class Jeu implements InfosPartie{
    private Pioche maPioche;
    private Joueur[] tableauJoueurs;
    private int nombreJoueurs;
    private Serie[] tableauSeries;
    private CarteJouee[] tourEnCours;
    private int prochaineCarte;
    private CarteJouee[] dernierTour;
    private Carte derniereCartePosee;
    private Joueur dernierRamasseur;
    private int dernierGain;
    private Joueur dernierPoseur;

    public Jeu(int nbJoueurs){
        this.maPioche = new Pioche();
        this.tableauJoueurs = new Joueur[10];
        this.tableauSeries = new Serie[4];
        this.nombreJoueurs = 0;
        for (int i=0; i<4; i++){
            tableauSeries[i] = new Serie();
        }
    }

    public Joueur getDernierPoseur() {
        return this.dernierPoseur;
    }

    public CarteJouee[] getDernierTour() {
        return dernierTour;
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

    public Joueur getDernierRamasseur() {
        return this.dernierRamasseur;
    }

    public int getDernierGain() {
        return this.dernierGain;
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

        dernierRamasseur = j;
        dernierGain = nombreDeTetes;

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
        return j.getStrategie().choisirCarte(j, this);
    }

    public int choisirSerieBot(Joueur j) {
        return j.getStrategie().choisirSerie(j, this);
    }

    public void preparerManche() {
        Pioche pioche = getPioche();
        pioche.reinitialiser();
        dernierTour = null;

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

    public void poserCarte(CarteJouee carteJouee) {
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
                poserCarte(cartesJouees[i]);
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

    public int trouverSerieMoinsChere() {
        int meilleureSerie = 0;
        int ptsSerie = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            Serie serie = getSerie(i);
            int nbTetes = serie.calculPoints();
            if (nbTetes < ptsSerie) {
                ptsSerie = nbTetes;
                meilleureSerie = i;
            }
        }

        return meilleureSerie;
    }

    public void preparerTour() {
        if (!tousLesHumainsOntChoisi()) {
            throw new IllegalStateException("Tous les humains n'ont pas encore choisi leur carte");
        }
        CarteJouee[] cartesJouees = new CarteJouee[getNombreJoueurs()];

        for (int i = 0; i < getNombreJoueurs(); i++) {
            Joueur joueur = getJoueur(i);
            if (!joueur.estAvatar()) {
                cartesJouees[i] = new CarteJouee(cartesChoisies[i], joueur);
            } else {
                cartesJouees[i] = new CarteJouee(choisirCarteBot(joueur), joueur);
            }
        }

        ordonnerCartesJouees(cartesJouees);
        dernierTour = cartesJouees;

        this.tourEnCours = cartesJouees;
        this.prochaineCarte = 0;

        for (int i = 0; i < cartesChoisies.length; i++) {
            cartesChoisies[i] = null;
        }
    }

    public void preparerTour(Joueur humain, Carte carteHumain) {
        choisirCarte(humain, carteHumain);
        preparerTour();
    }

    public boolean jouerTour(Joueur humain, Carte carteHumain) {
        preparerTour(humain, carteHumain);
        return continuerTour();
    }

    public void ramasserPourHumain(int numeroSerie) {
        dernierRamasseur = null;
        dernierGain = 0;

        CarteJouee cj = tourEnCours[prochaineCarte];
        derniereCartePosee = cj.getCarte();
        dernierPoseur = cj.getJoueur();
        ramasserSerie(numeroSerie, cj.getJoueur(), cj.getCarte());
        prochaineCarte++;
    }

    private boolean continuerTour() {
        EtatTour etat;
        do {
            etat = poserProchaineCarte();
        } while (etat == EtatTour.CARTE_POSEE);

        return etat == EtatTour.ATTENTE_CHOIX;
    }

    public boolean choisirSerieHumain(int numeroSerie) {
        ramasserPourHumain(numeroSerie);
        return continuerTour();
    }

    public boolean estTourEnCours() {
        return tourEnCours != null;
    }

    public boolean attendChoixSerie() {
        if (!estTourEnCours() || prochaineCarte >= tourEnCours.length) {
            return false;
        }
        CarteJouee cj = tourEnCours[prochaineCarte];
        return !cj.getJoueur().estAvatar() && trouverSerie(cj.getCarte()) == -1 ;
    }

    public boolean estMancheFinie() {
        return !estTourEnCours() && getJoueur(0).getMain().nombreDeCartes() == 0;
    }

    public int positionDe(Joueur joueur) {
        for (int i = 0; i < getNombreJoueurs(); i++) {
            if (getJoueur(i) == joueur) {
                return i;
            }
        }
        return -1;
    }

    public EtatTour poserProchaineCarte() {
        dernierRamasseur = null;
        dernierGain = 0;

        if (prochaineCarte >= tourEnCours.length) {
            tourEnCours = null;
            return EtatTour.TOUR_FINI;
        }

        CarteJouee cj = tourEnCours[prochaineCarte];

        if (attendChoixSerie()) {
            return EtatTour.ATTENTE_CHOIX;
        }

        derniereCartePosee = cj.getCarte();
        dernierPoseur = cj.getJoueur();
        poserCarte(cj);
        prochaineCarte++;
        return EtatTour.CARTE_POSEE;
    }

    public Carte getDerniereCartePosee() {
        return derniereCartePosee;
    }

    public Joueur[] getClassement() {
        // 1. copier les joueurs dans un nouveau tableau
        Joueur[] classement = new Joueur[getNombreJoueurs()];
        for (int i = 0; i < getNombreJoueurs(); i++) {
            classement[i] = getJoueur(i);
        }

        for (int i = 0; i < classement.length - 1; i++) {
            for (int j = i + 1; j < classement.length; j++) {

                if (classement[i].getNombreDePoints() > classement[j].getNombreDePoints()) {
                    Joueur tempJoueur = classement[i];
                    classement[i] = classement[j];
                    classement[j] = tempJoueur;
                }
            }
        }

        return classement;
    }

    private Carte[] cartesChoisies = new Carte[10];

    public void choisirCarte(Joueur joueur, Carte carte) {
        cartesChoisies[positionDe(joueur)] = carte;
    }

    public boolean tousLesHumainsOntChoisi() {
        for (int i = 0; i < getNombreJoueurs(); i++) {
            if (!getJoueur(i).estAvatar() && cartesChoisies[i] == null) {
                return false;
            }
        }
        return true;
    }

    public Joueur getJoueurQuiDoitChoisir() {
        if (!attendChoixSerie()) {
            return null;
        }
        return tourEnCours[prochaineCarte].getJoueur();
    }

    public boolean aChoisi(Joueur joueur) {
        return cartesChoisies[positionDe(joueur)] != null;
    }

    public String getNom(int position) {
        return tableauJoueurs[position].getNom();
    }

    public int getScore(int position) {
        return this.tableauJoueurs[position].getNombreDePoints();
    }

    public int getTetesPile(int position) {
        return this.tableauJoueurs[position].getPile().calculPoints();
    }

    public boolean estMoi(int position) {
        return !this.tableauJoueurs[position].estAvatar();
    }

    public boolean doisChoisirSerie() {
        return attendChoixSerie();
    }

    public Main getMaMain() {
        for (int i = 0; i < this.getNombreJoueurs(); i++) {
            if (!tableauJoueurs[i].estAvatar()) {
                return tableauJoueurs[i].getMain();
            }
        }
        return null;
    }

    public int getPoseur(int valeurCarte) {
        CarteJouee[] tour = getDernierTour();
        if (tour == null) {
            return -1;
        }
        for (int i = 0; i < tour.length; i++) {
            if (tour[i].getCarte().getValeur() == valeurCarte) {
                return positionDe(tour[i].getJoueur());
            }
        }
        return -1;
    }
}

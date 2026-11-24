package client;

import modele.Carte;
import modele.Main;
import modele.Serie;
import modele.InfosPartie;
import reseau.Protocole;

public class EtatClient implements InfosPartie {
    private int maPosition = -1;
    private String[] noms = new String[0];
    private Serie[] series = new Serie[4];
    private Main main = new Main();
    private int[][] scores = new int[0][];

    private boolean monTour = false;           // A_TOI_DE_JOUER reçu, et pas encore joué
    private boolean doisChoisirSerie = false;  // CHOISIS_SERIE reçu
    private int quiChoisit = -1;               // ATTENTE_SERIE : la position de celui qui choisit
    private int[] classement = null;           // FIN_PARTIE : null tant que la partie continue
    private String derniereErreur = null;

    private int[] valeursPosees = new int[10];
    private int[] positionsPoseurs = new int[10];
    private int nbPosees = 0;
    private boolean nouveauTour = true;

    public EtatClient() {
        for (int i = 0; i < 4; i++) {
            series[i] = new Serie();
        }
    }

    // le cœur de la classe : met à jour l'état selon la ligne reçue
    public void traiter(String ligne) {
        String commande = Protocole.commande(ligne);
        String donnees = Protocole.donnees(ligne);

        if (commande.equals("BIENVENUE")) {
            maPosition = Integer.parseInt(donnees);
        } else if (commande.equals("JOUEURS")) {
            noms = Protocole.decoderJoueurs(donnees);
        } else if (commande.equals("MAIN")) {
            main = new Main();
            int[] valeurs = Protocole.decoderValeurs(donnees);
            for (int i = 0; i < valeurs.length; i++) {
                main.ajouterCarte(new Carte(valeurs[i]));
            }
        } else if (commande.equals("SERIES")) {
            int[][] valeurs = Protocole.decoderSeries(donnees);
            for (int i = 0; i < 4; i++) {
                series[i].vider();
                for (int k = 0; k < valeurs[i].length; k++) {
                    series[i].ajouterCarte(new Carte(valeurs[i][k]));
                }
            }
        } else if (commande.equals("SCORES")) {
            scores = Protocole.decoderScores(donnees);
        } else if (commande.equals("A_TOI_DE_JOUER")) {
            nouveauTour = true;
            monTour = true;
            quiChoisit = -1;                   // plus personne ne choisit
        } else if (commande.equals("CHOISIS_SERIE")) {
            doisChoisirSerie = true;
        } else if (commande.equals("ATTENTE_SERIE")) {
            quiChoisit = Integer.parseInt(donnees);
        } else if (commande.equals("FIN_PARTIE")) {
            classement = Protocole.decoderClassement(donnees);
        } else if (commande.equals("ERREUR")) {
            derniereErreur = donnees;
        } else if (commande.equals("POSE")) {
            if (nouveauTour) {
                nbPosees = 0;
                nouveauTour = false;
            }
            int[] valeurs = Protocole.decoderValeurs(donnees);
            positionsPoseurs[nbPosees] = valeurs[0];
            valeursPosees[nbPosees] = valeurs[1];
            nbPosees++;
        }
    }

    public void aJoueUneCarte() {
        monTour = false;
    }

    public void aChoisiUneSerie() {
        doisChoisirSerie = false;
    }

    public int getPoseur(int valeurCarte) {
        for (int i = 0; i < nbPosees ; i++) {
            if (valeursPosees[i] == valeurCarte) {
                return positionsPoseurs[i];
            }
        }
        return -1;
    }

    public int getMaPosition() { return this.maPosition; }
    public String[] getNoms() { return this.noms; }
    public Serie getSerie(int i) { return this.series[i]; }
    public Main getMaMain() { return this.main; }
    public int[][] getScores() { return this.scores; }
    public boolean estMonTour() { return this.monTour; }
    public boolean doisChoisirSerie() { return this.doisChoisirSerie; }
    public int getQuiChoisit() { return this.quiChoisit; }
    public int[] getClassement() { return this.classement; }
    public String getDerniereErreur() { return this.derniereErreur; }
    public int getNombreJoueurs() { return this.noms.length; }
    public String getNom(int position) { return this.noms[position]; }
    public int getScore(int position) { return scores[position][0]; }
    public int getTetesPile(int position) { return scores[position][1]; }
    public boolean estMoi(int position) { return position == this.maPosition; }

}

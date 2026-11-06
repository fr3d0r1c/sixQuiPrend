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

    public Pioche getPioche() {
        return this.maPioche;
    }
}

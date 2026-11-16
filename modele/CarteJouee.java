package modele;

public class CarteJouee {
    private final  Carte carte;
    private final Joueur joueur;

    public CarteJouee(Carte c, Joueur j) {
        this.carte = c;
        this.joueur = j;
    }

    public Carte getCarte() {
        return this.carte;
    }

    public Joueur getJoueur() {
        return this.joueur;
    }
}

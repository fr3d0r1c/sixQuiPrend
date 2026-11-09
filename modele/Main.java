package modele;

public class Main {
    private Carte[] tableau;
    private int nombreCartes;

    public Main() {
        this.tableau = new Carte[10];
        this.nombreCartes = 0;
    }

    public int nombreDeCartes() {
        return this.nombreCartes;
    }

    public void ajouterCarte(Carte c) {
        if (nombreCartes < 10) {
            tableau[nombreCartes] = c;
            nombreCartes++;
        }
    }

    public Carte prendreUneCarte(int i) {
        Carte carteRetiree = tableau[i];

        for (int j = i; j < nombreCartes - 1; j++) {
            tableau[j] = tableau[j+1];
        }

        nombreCartes--;

        return carteRetiree;
    }

    @Override
    public String toString() {
        String resultat = "[";

        for (int i = 0; i < nombreCartes; i++) {
            resultat = resultat + tableau[i];

            if (i < nombreCartes - 1) {
                resultat = resultat + ",";
            }
        }

        resultat = resultat + "]";

        return resultat;
    }
}

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

    public Carte getCarte(int i) {
        return this.tableau[i];
    }

    public void trierMain() {
        for (int i = 0; i < this.nombreCartes - 1; i++) {
            for (int j = i + 1; j < nombreCartes; j++) {
                if (tableau[i].getValeur() > tableau[j].getValeur()) {
                    Carte tempCarte = tableau[i];
                    tableau[i] = tableau[j];
                    tableau[j] = tempCarte;
                }
            }
        }
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

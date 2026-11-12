package modele;

public class Serie {
    private Carte[] tableau;
    private int nombreCartes;

    public Serie() {
        this.tableau = new Carte[5];
        this.nombreCartes = 0;
    }

    public int nombreDeCartes() {
        return this.nombreCartes;
    }

    public Carte getCarte(int numero) {
        return this.tableau[numero];
    }

    public Carte derniereCarte() {
        if (this.nombreCartes == 0) {
            return null;
        }
        
        return this.tableau[this.nombreCartes - 1];
    }

    public void ajouterCarte(Carte carte) {
        if (nombreCartes < 5) {
            tableau[nombreCartes] = carte;
            nombreCartes++;
        }
    }

    public void vider() {
        this.nombreCartes = 0;
    }

    public int calculPoints() {
        int somme = 0;

        for (int i = 0; i < nombreCartes; i++) {
            Carte carte = tableau[i];
            int nombreTetes = carte.getNombreTetesDeBoeuf();
            
            somme += nombreTetes;
        }

        return somme;
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

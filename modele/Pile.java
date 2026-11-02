package modele;
import java.util.ArrayList;

public class Pile {
    private ArrayList<Carte> liste;

    public Pile() {
        this.liste = new ArrayList<>();
    }

    public void ajouterCarte(Carte carte) {
        liste.add(carte);
    }

    public void vider() {
        liste.clear();
    }

    public int calculPoints() {
        int somme = 0;

        for (int i = 0; i < liste.size(); i++){
            Carte carte = liste.get(i);
            int nombreTetes = carte.getNombreTetesDeBoeuf();

            somme += nombreTetes;
        }

        return somme;
    }

    @Override
    public String toString() {
        String resultat = "{";

        for (int i = 0; i < liste.size(); i++) {
            resultat = resultat + liste.get(i);

            if (i < liste.size() - 1) {
                resultat = resultat + ",";
            }
        }

        resultat = resultat + "}";

        return resultat;
    }
}

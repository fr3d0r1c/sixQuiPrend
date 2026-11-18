package modele;

public class Carte {
    private int valeur, nombreTetesDeBoeuf;

    private int calculNbTetes() {
        if (this.valeur == 55) {
            return 7;
        } else if (this.valeur % 11 == 0) {
            return 5;
        } else if (this.valeur % 10 == 0) {
            return 3;
        } else if (this.valeur % 5 == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    public Carte(int valeur) {
        if (valeur < 1 || valeur > 104) {
            this.valeur = 0;
            this.nombreTetesDeBoeuf = 0;
        } else {
            this.valeur = valeur;
            this.nombreTetesDeBoeuf = calculNbTetes();
        }
    }

    public int getValeur() {
        return this.valeur;
    }

    public int getNombreTetesDeBoeuf() {
        return this.nombreTetesDeBoeuf;
    }

    @Override
    public String toString() {
        return "(" + this.valeur + "," + this.nombreTetesDeBoeuf + ")";
    }

    public String[] toAsciiLines() {

        return new String[] {
            "+---------+",
            String.format("|   %3d   |", getValeur()),
            String.format("|  TB:%2d  |", getNombreTetesDeBoeuf()),
            "+---------+"
        };
    }
}
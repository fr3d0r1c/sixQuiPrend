package reseau;

import modele.Jeu;
import modele.Main;
import modele.Serie;
import modele.Joueur;

public class Protocole {
    public static String commande(String ligne) {
        int espace = ligne.indexOf(' ');
        if (espace == -1) {
            return ligne;
        }
        return ligne.substring(0, espace);
    }

    public static String donnees(String ligne) {
        int espace = ligne.indexOf(' ');
        if (espace == -1) {
            return "";
        }
        return ligne.substring(espace + 1);
    }

    public static String encoderMain(Main main) {
        String texte = "MAIN";
        for (int i = 0; i < main.nombreDeCartes(); i++) {
            texte = texte + " " + main.getCarte(i).getValeur();
        }
        return texte;
    }
    
    public static int[] decoderValeurs(String donnees) {
        if (donnees.isEmpty()) {
            return new int[0];                     // une main vide : un tableau de 0 case
        }
        String[] morceaux = donnees.split(" ");    // découpe le texte à chaque espace
        int[] valeurs = new int[morceaux.length];
        for (int i = 0; i < morceaux.length; i++) {
            valeurs[i] = Integer.parseInt(morceaux[i]);    // "27" (texte) → 27 (nombre)
        }
        return valeurs;
    }

    public static String encoderSeries(Jeu jeu) {
        String texte = "SERIES ";
        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                texte = texte + "|";
            }
            Serie serie = jeu.getSerie(i);
            for (int k = 0; k < serie.nombreDeCartes(); k++) {
                if (k > 0) {
                    texte = texte + ",";
                }
                texte = texte + serie.getCarte(k).getValeur();
            }
        }
        return texte;
    }

    public static int[][] decoderSeries(String donnees) {
        String[] morceauxSeries = donnees.split("\\|");
        int[][] series = new int[morceauxSeries.length][];
        for (int i = 0; i < morceauxSeries.length; i++) {
            String[] morceauxCartes = morceauxSeries[i].split(",");
            series[i] = new int[morceauxCartes.length];
            for (int k = 0; k < morceauxCartes.length; k++) {
                series[i][k] = Integer.parseInt(morceauxCartes[k]);
            }
        }
        return series;
    }

    public static String encoderScores(Jeu jeu) {
        String texte = "SCORES ";
        for (int i = 0; i < jeu.getNombreJoueurs(); i++) {
            if (i > 0) {
                texte = texte + "|";
            }
            Joueur joueur = jeu.getJoueur(i);
            int nbPoints = joueur.getNombreDePoints();
            int nbPile = joueur.getPile().calculPoints();

            texte = texte + nbPoints + "," + nbPile;
        }

        return texte;
    }

    public static int[][] decoderScores(String donnees) {
        return decoderSeries(donnees);
    }
}
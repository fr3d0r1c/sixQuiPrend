package reseau;

import modele.Main;

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
}

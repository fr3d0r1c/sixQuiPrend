package modele;

import java.util.Arrays;

public class SimulateurTour {
    
    public static int coutPour(Jeu jeu, int maCarte, int[] cartesAdverses) {
        // 1. la copie légère des 4 séries
        int[] derniere = new int[4];
        int[] nbCartes = new int[4];
        int[] tetes = new int[4];
        for (int i = 0; i < 4; i++) {
            Serie serie = jeu.getSerie(i);
            derniere[i] = serie.derniereCarte().getValeur();
            nbCartes[i] = serie.nombreDeCartes();
            tetes[i] = serie.calculPoints();
        }

        // 2. toutes les cartes du tour, triées (comme ordonnerCartesJouees)
        int[] cartes = new int[cartesAdverses.length + 1];
        cartes[0] = maCarte;
        for (int i = 0; i < cartesAdverses.length; i++) {
            cartes[i + 1] = cartesAdverses[i];
        }
        Arrays.sort(cartes);

        // 3. on pose chaque carte, dans l'ordre, avec les règles du jeu
        int cout = 0;
        for (int k = 0; k < cartes.length; k++) {
            int v = cartes[k];
            int tetesCarte = new Carte(v).getNombreTetesDeBoeuf();
            int s = serieCible(derniere, v);

            if (s == -1 || nbCartes[s] == 5) {
                if (s == -1) {
                    s = serieMoinsChere(tetes);       // trop petite : on ramasse la moins chère
                }
                if (v == maCarte) {
                    cout = cout + tetes[s];                // c'est MOI qui ramasse
                }
                derniere[s] = v;                      // la carte démarre une nouvelle série
                nbCartes[s] = 1;
                tetes[s] = tetesCarte;
            } else {
                derniere[s] = v;                      // la carte s'ajoute à la série
                nbCartes[s]++;
                tetes[s] = tetes[s] + tetesCarte;
            }
        }
        return cout;
    }

    private static int serieCible(int[] derniere, int v) {
        int meilleureSerie = -1;
		int meilleureValeur = -1;

        for (int i = 0; i < 4; i++) {
            int valeurSerie = derniere[i];

            if (valeurSerie < v) {
                if (valeurSerie > meilleureValeur) {
                    meilleureValeur = valeurSerie;
                    meilleureSerie = i;
                }
            }
        }

        return meilleureSerie;
    }

    private static int serieMoinsChere(int[] tetes) {
        int meilleureSerie = 0;
        int ptsSerie = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            int nbTetes = tetes[i];
            if (nbTetes < ptsSerie) {
                ptsSerie = nbTetes;
                meilleureSerie = i;
            }
        }

        return meilleureSerie;
    }

}

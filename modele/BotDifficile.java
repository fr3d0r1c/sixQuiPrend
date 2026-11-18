package modele;

public class BotDifficile implements StrategieBot {
    
    @Override
    public Carte choisirCarte(Joueur j, Jeu jeu) {
        Main main = j.getMain();

        int indexSur = -1;
        double meilleurRisque = Double.MAX_VALUE;

        int indexDangereux = -1;
        int meilleurCout = Integer.MAX_VALUE;

        for (int i = 0; i < main.nombreDeCartes(); i++) {
            Carte carte = main.getCarte(i);
            int numeroSerie = jeu.trouverSerie(carte);

            if (numeroSerie == -1) {
                // DANGER 1 : trop petite → on ramasserait une série
                int cout = jeu.getSerie(jeu.trouverSerieMoinsChere()).calculPoints();
                if (cout < meilleurCout) {
                    meilleurCout = cout;
                    indexDangereux = i;
                }
                continue;
            }

            Serie serie = jeu.getSerie(numeroSerie);

            if (serie.nombreDeCartes() == 5) {
                 int cout = serie.calculPoints();
                 if (cout < meilleurCout) {
                    meilleurCout = cout;
                    indexDangereux = i;
                 }
                 continue;
            }

            int ecart = carte.getValeur() - serie.derniereCarte().getValeur();
            int placesRestantes = 5 - serie.nombreDeCartes();
            double risque = (double) ecart / placesRestantes;

            if (risque < meilleurRisque) {
                meilleurRisque = risque;
                indexSur = i;
            }
        }

        if (indexSur != -1) {
            return main.prendreUneCarte(indexSur);
        }

        return main.prendreUneCarte(indexDangereux);
    }

    @Override
    public int choisirSerie(Joueur j, Jeu jeu) {
        return jeu.trouverSerieMoinsChere();
    }
}

package modele;

public class BotMoyen implements StrategieBot {

    @Override
    public Carte choisirCarte(Joueur j, Jeu jeu) {
        Main main = j.getMain();
        int meilleurIndex = -1;
        int meilleurEcart  = Integer.MAX_VALUE;

        for (int i = 0; i < main.nombreDeCartes(); i++) {
            Carte carte = main.getCarte(i);
            int numeroSerie = jeu.trouverSerie(carte);

            if(numeroSerie == -1) {
                continue;
            }

            Serie serie = jeu.getSerie(numeroSerie);
            if (serie.nombreDeCartes() == 5) {
                continue;
            }

            int ecart = carte.getValeur() - serie.derniereCarte().getValeur();
            if (ecart < meilleurEcart) {
                meilleurEcart = ecart;
                meilleurIndex = i;
            }
        }

        if (meilleurIndex == -1) {
            meilleurIndex = 0;
        }

        return main.prendreUneCarte(meilleurIndex);
    }

    @Override
    public int choisirSerie(Joueur j, Jeu jeu) {
        return jeu.trouverSerieMoinsChere();
    }
}

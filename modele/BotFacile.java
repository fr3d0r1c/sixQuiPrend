package modele;

public class BotFacile implements StrategieBot {
    
    @Override
    public Carte choisirCarte(Joueur j, Jeu jeu) {
        return j.getMain().prendreUneCarte(0);
    }

    @Override
    public int choisirSerie(Joueur j, Jeu jeu) {
        return jeu.trouverSerieMoinsChere();
    }
}

package modele;

public interface StrategieBot {
    Carte choisirCarte(Joueur j, Jeu jeu);
    int choisirSerie(Joueur j, Jeu jeu);
}

package modele;

public interface InfosPartie {
    Serie getSerie(int numero);

    int getNombreJoueurs();
    String getNom(int position);
    int getScore(int position);
    int getTetesPile(int position);
    boolean estMoi(int position); 

    Main getMaMain();
    boolean doisChoisirSerie();

    int getPoseur(int valeurCarte);
}

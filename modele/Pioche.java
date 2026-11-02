package modele;

import java.util.Random;

public class Pioche {
    private Carte[] liste;
    private int nombreCartesPiochees;

    public Pioche() {
        this.liste = new Carte[104];
        this.nombreCartesPiochees = 0;
        for (int i = 0; i < 104; i++){
            liste[i] = new Carte(i + 1);
        }

        melanger();
    }

    private void melanger() {
        Random r = new Random();

        for (int k = 0; k < 1000; k++) {
            int i = r.nextInt(104);
            int j = r.nextInt(104);

            Carte temp = liste[i];
            liste[i] = liste[j];
            liste[j] = temp;
        }
    }

    public int nombreCartesDisponibles() {
        int nombreCarteDisponible = 104 - this.nombreCartesPiochees;
        return nombreCarteDisponible;
    }

    public Carte getCarte(int numero) {
        return this.liste[numero];
    }

    public boolean estDisponible(int numero) {
        if (numero < 0 || numero >= 104) {
            return false;
        }
        return numero >= nombreCartesPiochees;
    }

    public void reinitialiser() {
        melanger();
        this.nombreCartesPiochees = 0;
    }

    public Carte piocher() {
        if (nombreCartesPiochees == 104) {
            return null;
        } 
        
        Carte carte = liste[nombreCartesPiochees];
        nombreCartesPiochees++;

        return carte;
    }
}

package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import modele.Joueur;

public class ClientJeu implements Runnable {
    private final Socket socket;
    private final ServeurJeu serveur;
    private final PrintWriter sortie;
    private Joueur joueur;

    public ClientJeu(Socket socket, ServeurJeu serveur) throws IOException {
        this.socket = socket;
        this.serveur = serveur;
        this.sortie = new PrintWriter(socket.getOutputStream(), true);   // existe AVANT que le client soit visible des autres
    }

    public void envoyer(String message) {
        sortie.println(message);
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public void run() {
        try {
            BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // CHANGE : plus de pseudo à lire en premier, chaque ligne va au serveur
            String ligne;
            while ((ligne = entree.readLine()) != null) {
                serveur.recevoir(this, ligne);
            }
        } catch (IOException e) {
            System.out.println("Connexion perdue : " + e.getMessage());
        } finally {
            serveur.retirer(this);
        }
    }
}

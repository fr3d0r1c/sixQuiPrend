package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GestionClient implements Runnable {
    private final Socket socket;
    private final ServeurChat serveur;
    private PrintWriter sortie;

    public GestionClient(Socket socket, ServeurChat serveur) {
        this.socket = socket;
        this.serveur = serveur;
    }

    public void envoyer(String message) {
        sortie.println(message);
    }

    @Override
    public void run() {
        String pseudo = "un joueur";
        try {
            BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sortie = new PrintWriter(socket.getOutputStream(), true);
            
            pseudo = entree.readLine();
            serveur.diffuser(pseudo + " a rejoint le chat");

            String ligne;
            while ((ligne = entree.readLine()) != null) {
                serveur.diffuser(pseudo + " : " + ligne);
            }
        } catch (IOException e) {
            System.out.println("Connexion perdue : " + e.getMessage());
        } finally {
            serveur.retirer(this);                    // quoi qu'il arrive, on le retire de la liste
            serveur.diffuser(pseudo + " a quitté le chat");
        }
    }
}
package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurEcho {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serveur = new ServerSocket(5555)) {
            System.out.println("Serveur en attente sur le port 5555...");

            Socket client = serveur.accept();

            System.out.println("Client connecté : " + client.getInetAddress());

            BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter sortie = new PrintWriter(client.getOutputStream(), true);

            String ligne;
            while ((ligne = entree.readLine()) != null) {
                System.out.println("Reçu : " + ligne);
                sortie.println("Écho : " + ligne);
            }

            System.out.println("Client déconnecté.");
            client.close();
            serveur.close();
        }
    }
}

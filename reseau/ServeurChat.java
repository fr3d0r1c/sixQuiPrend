package reseau;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServeurChat {
    private static final int PORT = 5555;
    private final List<GestionClient> clients = new ArrayList<>();   // import java.util.*

    public static void main(String[] args) throws IOException {
        new ServeurChat().demarrer();
    }

    private void demarrer() throws IOException {
        try (ServerSocket serveur = new ServerSocket(PORT)) {
            System.out.println("Serveur de chat sur le port " + PORT);
            while (true) {
                Socket socket = serveur.accept();
                GestionClient client = new GestionClient(socket, this);   // le client doit connaître le serveur
                ajouter(client);
                new Thread(client).start();                                 // son propre thread
            }
        }
    }

    public synchronized void ajouter(GestionClient client) {
        clients.add(client);
    }

    public synchronized void retirer(GestionClient client) {
        clients.remove(client);
    }

    // envoie un message à TOUS les clients connectés
    public synchronized void diffuser(String message) {
        System.out.println(message);
        for (GestionClient client : clients) {
            client.envoyer(message);
        }
    }
}
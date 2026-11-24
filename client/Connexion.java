package client;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;
import javafx.application.Platform;

public class Connexion {
    private final Socket socket;
    private final PrintWriter sortie;

    public Connexion(String adresse, int port, Consumer<String> quandMessage) throws IOException {
        socket = new Socket(adresse, port);
        sortie = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread lecteur = new Thread(() -> {
            try {
                String ligne;
                while ((ligne = entree.readLine()) != null) {
                    final String message = ligne;  
                    Platform.runLater(() -> quandMessage.accept(message));    // exécuté par JavaFX
                }
            } catch (IOException e) {

            }
            Platform.runLater(() -> quandMessage.accept("DECONNECTE"));
        });
        lecteur.setDaemon(true);
        lecteur.start();
    }

    public void envoyer(String message) {
        sortie.println(message);
    }

    public void fermer() throws IOException {
        socket.close();
    }
}

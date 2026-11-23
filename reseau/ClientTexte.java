package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTexte {
    public static void main(String[] args) throws IOException {
        String adresse = "localhost";
        if (args.length > 0) {
            adresse = args[0];
        }
        Socket socket = new Socket(adresse, 5555);
        System.out.println("Connecté au serveur !");

        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
        Scanner clavier = new Scanner(System.in);

        new Thread(() -> {
            try {
                String ligne;
                while ((ligne = entree.readLine()) != null) {
                    System.out.println(ligne);
                }
            } catch (IOException e) {
                System.out.println("Déconnecté du serveur.");
            }
        }).start();

        System.out.print("> ");

        while (clavier.hasNextLine()) {
            String message = clavier.nextLine();
            if (message.equals("quitter")) {
                break;
            }
            sortie.println(message);
            System.out.print("> ");
        }

        socket.close();
    }
}

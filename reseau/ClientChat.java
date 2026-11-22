package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Connecté au serveur !");

        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
        Scanner clavier = new Scanner(System.in);

        System.out.print("Quel est votre pseudo ? ");
        String pseudo = clavier.nextLine();
        sortie.println(pseudo);

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

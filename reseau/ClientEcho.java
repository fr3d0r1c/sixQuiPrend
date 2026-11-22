package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientEcho {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Connecté au serveur !");

        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
        Scanner clavier = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String message = clavier.nextLine();
            if (message.equals("quitter")) {
                break;
            }
            sortie.println(message);
            String reponse = entree.readLine();
            System.out.println(reponse);
        }

        socket.close();
    }
}

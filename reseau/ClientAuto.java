package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientAuto {
    public static void main(String[] args) throws IOException {
        String pseudo = args[0];
        Socket socket = new Socket("localhost", 5555);
        BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true); 

        sortie.println("NOM " + pseudo);
        int[] main = new int[0];

        String ligne;
        while ((ligne = entree.readLine()) != null) {
            System.out.println(ligne);
            String commande = Protocole.commande(ligne);
            String donnees = Protocole.donnees(ligne);

            if (commande.equals("MAIN")) {
                main = Protocole.decoderValeurs(donnees);
            } else if (commande.equals("A_TOI_DE_JOUER")) {
                sortie.println("CARTE " + main[0]);
            } else if (commande.equals("CHOISIS_SERIE")) {
                sortie.println("SERIE " + 0);
            } else if (commande.equals("FIN_PARTIE")) {
                System.out.println("Partie terminée !");
                break;
            }
        }
        socket.close();
    }
}

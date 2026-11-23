package reseau;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import modele.Jeu;
import modele.Joueur;
import modele.TypeBot;

public class ServeurJeu {
    private static final int PORT = 5555;
    
    private final List<ClientJeu> clients = new ArrayList<>();

    private final int nbHumains;
    private final int nbBots;
    private final Jeu jeu;

    public ServeurJeu(int nbHumains, int nbBots) {
        this.nbHumains = nbHumains;
        this.nbBots = nbBots;
        this.jeu = new Jeu(nbHumains+nbBots);
    }

    public static void main(String[] args) throws IOException {
        int nbHumains = Integer.parseInt(args[0]);
        int nbBots = Integer.parseInt(args[1]);
        new ServeurJeu(nbHumains, nbBots).demarrer();
    }

    private void demarrer() throws IOException {
        try (ServerSocket serveur = new ServerSocket(PORT)) {
            System.out.println("Serveur de jeu sur le port " + PORT
                + " (" + nbHumains + " humains, " + nbBots + " bots)");
            while (true) {
                Socket socket = serveur.accept();
                ClientJeu client = new ClientJeu(socket, this);
                ajouter(client);
                new Thread(client).start();
            }
        }
    }

    public synchronized void ajouter(ClientJeu client) {
        clients.add(client);
    }

    public synchronized void retirer(ClientJeu client) { 
        clients.remove(client);
     }
    public synchronized void diffuser(String message) {
        System.out.println(message);
        for (ClientJeu client : clients) {
            client.envoyer(message);
        }
    }

    public synchronized void recevoir(ClientJeu client, String ligne) {
        String commande = Protocole.commande(ligne);
        String donnees = Protocole.donnees(ligne);

        if (commande.equals("NOM")) {
            traiterNom(client, donnees);
        } else {
            client.envoyer("ERREUR Commande inconnue : " + commande);
        }
    }

    private void traiterNom(ClientJeu client, String pseudo) {
        // 1. les refus
        if (pseudo.isEmpty() || pseudo.length() > 15 || pseudo.contains(";")) {
            client.envoyer("ERREUR Pseudo invalide (1 à 15 caractères, sans ;)");
            return;
        }
        if (client.getJoueur() != null) {
            client.envoyer("ERREUR Tu as déjà un pseudo");
            return;
        }
        if (jeu.getNombreJoueurs() >= nbHumains) {
            client.envoyer("ERREUR La partie est complète");
            return;
        }

        // 2. on l'accepte
        Joueur joueur = new Joueur(pseudo, false);
        jeu.ajouterJoueur(joueur);
        client.setJoueur(joueur);
        client.envoyer("BIENVENUE " + jeu.positionDe(joueur));

        // 3. le dernier humain attendu est arrivé : on complète avec les bots, puis on prévient tout le monde
        if (jeu.getNombreJoueurs() == nbHumains) {
            for (int i = 1; i <= nbBots; i++) {
                jeu.ajouterJoueur(new Joueur("Bot " + i, true, TypeBot.MOYEN));
            }
            diffuser(Protocole.encoderJoueurs(jeu));
             demarrerManche();
        }
    }

    private void demarrerManche() {
        jeu.preparerManche();

        diffuser(Protocole.encoderSeries(jeu));
        diffuser(Protocole.encoderScores(jeu));

        for (ClientJeu client : clients) {
            Joueur joueur = client.getJoueur();
            if (joueur != null) {
                client.envoyer(Protocole.encoderMain(joueur.getMain()));
                client.envoyer("A_TOI_DE_JOUER");
            }
        }
    }
}

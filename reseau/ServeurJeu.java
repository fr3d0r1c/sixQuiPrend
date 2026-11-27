package reseau;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import modele.Carte;
import modele.EtatTour;
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
        } else if (commande.equals("CARTE")) {
            traiterCarte(client, donnees);
        } else if (commande.equals("SERIE")) {
            traiterSerie(client, donnees);
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

    private void traiterCarte(ClientJeu client, String donnees) {
        Joueur joueur = client.getJoueur();

        // 1. les refus
        if (joueur == null) {
            client.envoyer("ERREUR Envoie d'abord ton pseudo avec NOM");
            return;
        }
        if (jeu.estTourEnCours()) {
            client.envoyer("ERREUR Un tour est déjà en cours");
            return;
        }
        if (jeu.aChoisi(joueur)) {
            client.envoyer("ERREUR Tu as déjà choisi ta carte pour ce tour");
            return;
        }

        int valeur;
        try {
            valeur = Integer.parseInt(donnees);
        } catch (NumberFormatException e) {
            client.envoyer("ERREUR " + donnees + " n'est pas un numéro de carte");
            return;
        }

        int position = joueur.getMain().trouverCarte(valeur);
        if (position == -1) {
            client.envoyer("ERREUR Cette carte n'est pas dans ta main");
            return;
        }

        // 2. on l'accepte : la carte quitte la main, et elle est mémorisée
        Carte carte = joueur.getMain().prendreUneCarte(position);
        jeu.choisirCarte(joueur, carte);
        client.envoyer(Protocole.encoderMain(joueur.getMain()));   // sa main, sans la carte jouée

        // 3. le dernier humain vient de choisir : on joue le tour
        if (jeu.tousLesHumainsOntChoisi()) {
            jeu.preparerTour();
            resoudreTour();
        }
    }

    private void resoudreTour() {
        EtatTour etat = jeu.poserProchaineCarte();

        // tant que des cartes sont posées : on prévient tout le monde, carte par carte
        while (etat == EtatTour.CARTE_POSEE) {
            annoncerDerniereEtape();
            etat = jeu.poserProchaineCarte();
        }

        // l'état complet, pour que tout le monde soit synchronisé
        diffuser(Protocole.encoderSeries(jeu));
        diffuser(Protocole.encoderScores(jeu));

        if (etat == EtatTour.ATTENTE_CHOIX) {
            demanderChoixSerie();
        }  else if (jeu.estMancheFinie()) {
            finirManche();
        } else {
            for (ClientJeu client : clients) {
                if (client.getJoueur() != null) {
                    client.envoyer("A_TOI_DE_JOUER");
                }
            }
        }
    }

    private void demanderChoixSerie() {
        Joueur choisisseur = jeu.getJoueurQuiDoitChoisir();
        int position = jeu.positionDe(choisisseur);

        for (ClientJeu client : clients) {
            if (client.getJoueur() == choisisseur) {
                client.envoyer("CHOISIS_SERIE");
            } else if (client.getJoueur() != null) {
                client.envoyer("ATTENTE_SERIE " + position);
            }
        }
    }

    private void annoncerDerniereEtape() {
        diffuser("POSE " + jeu.positionDe(jeu.getDernierPoseur()) + " " + jeu.getDerniereCartePosee().getValeur());
        if (jeu.getDernierRamasseur() != null) {
             diffuser("RAMASSE " + jeu.positionDe(jeu.getDernierRamasseur()) + " " + jeu.getDernierGain());
        }
        diffuser(Protocole.encoderSeries(jeu));
        diffuser(Protocole.encoderScores(jeu));
    }

    private void traiterSerie(ClientJeu client, String donnees) {
        Joueur joueur = client.getJoueur();

        // 1. les refus
        if (joueur == null) {
            client.envoyer("ERREUR Envoie d'abord ton pseudo avec NOM");
            return;
        }
        if (!jeu.attendChoixSerie()) {
            client.envoyer("ERREUR Aucun choix de série n'est attendu pour l'instant");
            return;
        }
        if (joueur != jeu.getJoueurQuiDoitChoisir()) {
            client.envoyer("ERREUR Ce n'est pas à toi de choisir la série");
            return;
        }

        int numero;
        try {
            numero = Integer.parseInt(donnees);
        } catch (NumberFormatException e) {
            client.envoyer("ERREUR " + donnees + " n'est pas un numéro de série");
            return;
        }
        if (numero < 0 || numero > 3) {
            client.envoyer("ERREUR Mauvais numéro de série");
            return;
        }

        jeu.ramasserPourHumain(numero);
        annoncerDerniereEtape();
        resoudreTour();
    }

    public static String encoderClassement(Jeu jeu) {
        Joueur[] classement = jeu.getClassement();
        String texte = "FIN_PARTIE ";
        for (int i=0; i < classement.length; i++) {
            if (i>0) {
                texte = texte + ";";
            }
            texte = texte + jeu.positionDe(classement[i]);
        }
        return texte;
    }

    private void finirManche() {
        jeu.terminerManche();
        diffuser(Protocole.encoderScores(jeu));
        diffuser("FIN_MANCHE");

        if (jeu.estFinie()) {
            diffuser(Protocole.encoderClassement(jeu));
        } else {
            demarrerManche();
        }
    }
}

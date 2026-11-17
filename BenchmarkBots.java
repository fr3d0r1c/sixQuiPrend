import modele.*;
public class BenchmarkBots {
    
    public static void main(String[] args) {

        int nbParties = 1000;

        int victoiresBotAleatoire = 0;
        int victoiresBotFacile = 0;

        int scoreCumuleBotAleatoire = 0;
        int scoreCumuleBotFacile = 0;

        System.out.println("================================");
        System.out.println("BENCHMARK DES BOTS");
        System.out.println("================================");

        for (int i = 0; i < nbParties; i++) {

            Jeu jeu = new Jeu(2);
            Joueur botAleatoire = new Joueur("Aleatoire", true, TypeBot.ALEATOIRE);
            Joueur botFacile = new Joueur("Facile", true, TypeBot.FACILE);
            jeu.ajouterJoueur(botFacile);
            jeu.ajouterJoueur(botAleatoire);

            jeu.jouerPartieAutomatique();

            int scoreAleatoire = botAleatoire.getNombreDePoints();
            int scoreFacile = botFacile.getNombreDePoints();

            scoreCumuleBotAleatoire += scoreAleatoire;
            scoreCumuleBotFacile += scoreFacile;

            if (scoreAleatoire < scoreFacile) {
                victoiresBotAleatoire++;
            }
            else if (scoreFacile < scoreAleatoire) {
                victoiresBotFacile++;
            }
        }

        System.out.println();
        System.out.println("================================");
        System.out.println("RESULTATS");
        System.out.println("================================");

        System.out.println(
            "Bot Aleatoire : "
            + victoiresBotAleatoire
            + " victoires"
        );

        System.out.println(
            "Bot Facile : "
            + victoiresBotFacile
            + " victoires"
        );

        System.out.println();

        System.out.println(
            "Score moyen Bot Aleatoire : "
            + ((double) scoreCumuleBotAleatoire / nbParties)
        );

        System.out.println(
            "Score moyen Bot Facile : "
            + ((double) scoreCumuleBotFacile / nbParties)
        );
    }
}

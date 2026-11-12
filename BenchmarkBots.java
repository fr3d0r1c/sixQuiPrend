public class BenchmarkBots {
    
    public static void main(String[] args) {

        int nbParties = 100;

        int victoiresBotAleatoire = 0;
        int victoiresBotFacile = 0;

        int scoreCumuleBotAleatoire = 0;
        int scoreCumuleBotFacile = 0;

        System.out.println("================================");
        System.out.println("BENCHMARK DES BOTS");
        System.out.println("================================");

        for (int i = 0; i < nbParties; i++) {

            System.out.println(
                "Simulation de la partie"
                + (i + 1)
            );

            /*
             * Ici viendra :
             *
             * 1. Création d'une partie
             * 2. Création du bot aléatoire
             * 3. Création du bot facile
             * 4. Lancement automatique de la partie
             * 5. Récupération des scores
             */

            int scoreAleatoire = 0;
            int scoreFacile = 0;

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

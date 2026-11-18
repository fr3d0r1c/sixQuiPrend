import modele.*;
public class BenchmarkBots {
    
    public static void main(String[] args) {

        TypeBot[] participants = { TypeBot.FACILE, TypeBot.ALEATOIRE, TypeBot.FACILE, TypeBot.ALEATOIRE };

        int nbParties = 1000;
        int egalites = 0;

        int nbTypes = TypeBot.values().length;
        int[] victoires = new int[nbTypes];
        int[] scoreCumule = new int[nbTypes];
        int[] nbJoueursParTypes = new int[nbTypes];

        System.out.println("================================");
        System.out.println("BENCHMARK DES BOTS");
        System.out.println("================================");

        for (int i = 0; i < nbParties; i++) {
            
            Jeu jeu = new Jeu(participants.length);
            for (int j = 0; j < participants.length; j++) {
                TypeBot type = participants[j];
                Joueur bot = new Joueur("Bot" + j, true, type);
                jeu.ajouterJoueur(bot);
            }

            jeu.jouerPartieAutomatique();

            for (int j = 0; j < jeu.getNombreJoueurs(); j++) {
                Joueur joueur = jeu.getJoueur(j);
                int indexType = joueur.getTypeBot().ordinal();

                scoreCumule[indexType] += joueur.getNombreDePoints();
                nbJoueursParTypes[indexType]++;
            }

            if (jeu.estEgalite() == true) {
                egalites++;
            } else {
                Joueur gagnant = jeu.trouverGagnant();
                victoires[gagnant.getTypeBot().ordinal()]++;
            }
        }

        System.out.println();
        System.out.println("================================");
        System.out.println("RESULTATS");
        System.out.println("================================");

        for (TypeBot type : TypeBot.values()) {
            int index = type.ordinal();

            if (nbJoueursParTypes[index] > 0) {
                double scoreMoyen = (double) scoreCumule[index] / nbJoueursParTypes[index];
                System.out.println(
                    "Bot " + type
                    + " : " + victoires[index] + " victoires"
                    + ", score moyen " + scoreMoyen
                );
            }
        }

        System.out.println(
            "Parties avec une égalités : " 
            + egalites
            + " parties"
        );
    }
}

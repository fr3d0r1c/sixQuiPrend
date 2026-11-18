import modele.*;

public class BenchmarkBots {
    public static void main(String[] args) {
        int nbParties = 1000;
        int nbTypes = TypeBot.values().length;

        // ===== 1. TOURNOI À 4 =====
        TypeBot[] participants = { TypeBot.ALEATOIRE, TypeBot.FACILE, TypeBot.MOYEN, TypeBot.DIFFICILE };
        int[] scoreCumule = new int[nbTypes];
        int[] nbJoueursParTypes = new int[nbTypes];

        System.out.println("================================");
        System.out.println("BENCHMARK DES BOTS");
        System.out.println("================================");

        int[] victoires = jouerTournoi(participants, nbParties, scoreCumule, nbJoueursParTypes);

        int totalVictoires = 0;
        for (int v : victoires) {
            totalVictoires += v;
        }
        
        int egalites = nbParties - totalVictoires;

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
            "Parties avec une égalité : " 
            + egalites
            + " parties"
        );

        // ===== 2. MATRICE DES DUELS =====
        System.out.println();
        System.out.println("================================");
        System.out.println("DUELS (" + nbParties + " parties chacun)");
        System.out.println("================================");

        TypeBot[] types = TypeBot.values();
        for (int i = 0; i < types.length; i++) {
            for (int k = i+1; k < types.length; k++) {
                TypeBot[] duel = { types[i], types[k] };
                int[] v = jouerTournoi(duel, nbParties, new int[nbTypes], new int[nbTypes]);
                int egalitesDuel = nbParties - v[types[i].ordinal()] - v[types[k].ordinal()];
                System.out.println(types[i] + " vs " + types[k] + " : "
                    + v[types[i].ordinal()] + " - " + v[types[k].ordinal()] + " (" + egalitesDuel + " matchs nuls)");
            }
        }
    }

    private static int[] jouerTournoi(TypeBot[] participants, int nbParties, int[] scoreCumule, int[] nbJoueursParTypes) {
        int[] victoires = new int[TypeBot.values().length];

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

            if (!jeu.estEgalite()) {
                Joueur gagnant = jeu.trouverGagnant();
                victoires[gagnant.getTypeBot().ordinal()]++;
            }
        }

        return victoires;
    }
}
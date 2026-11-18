package modele;

public enum TypeBot {
    ALEATOIRE,
    FACILE,
    MOYEN,
    DIFFICILE;

    public StrategieBot creerStrategie() {
        switch(this) {
            case ALEATOIRE:
                return new BotAleatoire();
            
            case FACILE:
                return new BotFacile();

            case MOYEN:
                return new BotMoyen();
            
            case DIFFICILE:
                return new BotDifficile();

            default:
                throw new IllegalStateException("Type de bot non géré : " + this);
        }
    }
}

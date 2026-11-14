package modele;

public class Joueur {
    private String nom;
    private Pile maPile;
    private Main maMain;
    private int nombreDePoints;
    private boolean avatar;
    private TypeBot typeBot;

    public Joueur(String nom, boolean estUnAvatar, TypeBot typeBot) {
        this.nom = nom;
        this.avatar = estUnAvatar;
        if (estUnAvatar) {
            this.typeBot = typeBot;
        }
        this.nombreDePoints = 0;
        this.maPile = new Pile();
        this.maMain = new Main();
    }

    public Joueur(String nom, boolean estUnAvatar) {
        this(nom, estUnAvatar, TypeBot.ALEATOIRE);
    }

    public String getNom() {
        return this.nom;
    }

    public TypeBot getTypeBot() {
        return this.typeBot;
    }

    public int getNombreDePoints() {
        return this.nombreDePoints;
    }

    public Pile getPile() {
        return this.maPile;
    }

    public Main getMain() {
        return this.maMain;
    }

    public boolean estAvatar() {
        return this.avatar;
    }

    public void ajouterPoints(int nbPoints) {
        this.nombreDePoints = this.nombreDePoints + nbPoints;
    }

}

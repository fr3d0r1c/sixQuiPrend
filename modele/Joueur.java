package modele;

public class Joueur {
    private String nom;
    private Pile maPile;
    private Main maMain;
    private int nombreDePoints;
    private boolean avatar;

    public Joueur(String nom, boolean estUnAvatar) {
        this.nom = nom;
        this.avatar = estUnAvatar;
        this.nombreDePoints = 0;
        this.maPile = new Pile();
        this.maMain = new Main();
    }

    public String getNom() {
        return this.nom;
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

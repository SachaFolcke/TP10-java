package tp10;

public enum Niveau {
    SIXIEME("Sixième"),
    CINQUIEME("Cinquième"),
    QUATRIEME("Quatrième"),
    TROISIEME("Troisième");

    private String nom;

    private Niveau(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public String toString() {
        return this.nom;
    }



}

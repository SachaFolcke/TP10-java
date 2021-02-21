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

    /**
     * Retourne l'objet Niveau existant qui possède le même nom que celui donné
     * @param nom Nom à chercher
     * @return Un objet Niveau ou null
     */
    public static Niveau getNiveauParNom(String nom) {
        for(Niveau n : Niveau.values()) {
            if(n.getNom().equals(nom))
                return n;
        }
        return null;
    }


}

package tp10;

public enum NumClasse {

    A("A"), B("B"), C("C"), D("D"), E("E"), F("F");

    private String nom;

    NumClasse(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'objet NumClasse existant qui possède le même nom que celui donné
     * @param nom Nom à chercher
     * @return Un objet NumClasse ou null
     */
    public static NumClasse getNumParNom(String nom) {
        for(NumClasse n : NumClasse.values()) {
            if(n.getNom().equals(nom))
                return n;
        }
        return null;
    }
}

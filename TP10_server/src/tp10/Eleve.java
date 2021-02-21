package tp10;

import java.util.*;

public class Eleve {

    private String nom;
    private String prenom;
    private Map<Matiere, ArrayList<Double>> bulletin;
    private List<Matiere> options;

    public Eleve(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.bulletin = new LinkedHashMap<>();
        this.options = new ArrayList<>();
    }

    /**
     * Génère aléatoirement des options avec des chances arbitraires
     * @param m1 Objet Matiere ne devant pas être ajouté avec m2 (ici grec)
     * @param m2 Objet Matiere ne devant pas être ajouté avec m1 (ici latin)
     * @param m3 Objet Matiere
     */
    public void genererOptions(Matiere m1, Matiere m2, Matiere m3) {
        Random r = new Random();

        if(r.nextInt(10) < 3) {
            if (r.nextBoolean()) {
                this.options.add(m1);
            } else {
                this.options.add(m2);
            }
        }

        if(r.nextInt(10) < 3) {
            this.options.add(m3);
        }
    }

    /**
     * Ajoute la note au bulletin de l'élève. La note est ramenée entre 0 et 20 si en dehors.
     * @param matiere Objet Matiere concerné
     * @param note Double représentant la note
     */
    public void ajouterNote(Matiere matiere, double note) {
        if(note > 20.0) {
            note = 20.0;
        } else if (note < 0.0) {
            note = 0.0;
        }

        if(!this.bulletin.containsKey(matiere)) {
            this.bulletin.put(matiere, new ArrayList<>());
        }

        this.bulletin.get(matiere).add(note);

    }

    public List<Matiere> getOptions() {
        return options;
    }

    public Map<Matiere, ArrayList<Double>> getBulletin() {
        return bulletin;
    }

    public String toString() {
        return this.prenom + " " + this.nom + " "
                + (this.options.size() == 1 ? "(" + this.options.get(0) + ")"
                : this.options.size() == 2 ? "(" + this.options.get(0) + ", " + this.options.get(1) + ")"
                : "");
    }
}

package tp10;

import java.util.LinkedHashMap;
import java.util.Map;

public class Epreuve {

    private Matiere matiere;
    private Map<Eleve, Double> notes;

    public Epreuve(Matiere matiere) {
        this.matiere = matiere;
        this.notes = new LinkedHashMap<>();
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Map<Eleve, Double> getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return this.matiere.toString();
    }
}

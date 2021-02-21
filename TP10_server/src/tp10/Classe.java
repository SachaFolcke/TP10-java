package tp10;

import java.util.ArrayList;
import java.util.List;

public class Classe {

    private Niveau niveau;
    private NumClasse numero;
    private List<Eleve> eleves;

    public Classe(Niveau niveau, NumClasse numero) {
        this.niveau = niveau;
        this.numero = numero;
        this.eleves = new ArrayList<>();
    }

    public void ajouterEleve(Eleve e) {
        this.eleves.add(e);
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public String toString() {
        return niveau.getNom() + " " + numero;
    }
}

package tp10;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BulletinView {
    private Matiere matiere;
    private Double note1, note2, note3, moyenne;

    public BulletinView(Matiere matiere) {
        this.matiere = matiere;
    }

    public BulletinView(Matiere matiere, Double note1, Double note2, Double note3) {
        this.matiere = matiere;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
        makeMoyenne();
    }

    public BulletinView(Matiere matiere, ArrayList<Double> notes) {
        this(matiere);

        this.note1 = notes.get(0);
        this.note2 = notes.get(1);

        if(notes.size() > 2) {
            this.note3 = notes.get(2);
        }

        makeMoyenne();
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Double getNote1() {
        return note1;
    }

    public Double getNote2() {
        return note2;
    }

    public Double getNote3() {
        return note3;
    }

    public void makeMoyenne() {
        DecimalFormat df = new DecimalFormat("##.##");
        if(note3 == null) {
            this.moyenne = Math.floor((((note1 + note2) / 2) * 100)) / 100;
        } else {
            this.moyenne = Math.floor((((note1 + note2 + note3) / 3) * 100)) / 100;
        }
    }

    public Double getMoyenne() {
        return moyenne;
    }
}

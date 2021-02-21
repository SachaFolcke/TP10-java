package tp10;

import java.util.Objects;

public class Matiere {

    private final MatiereMeta infos;

    public Matiere(MatiereMeta infos) {
        this.infos = infos;
    }

    public int getNotesAttendues() {
        return infos.getNotesAttendues();
    }

    public int getNiveauMinimum() {
        return infos.getNiveauMinimum();
    }

    public boolean isOption() {
        return infos.isOption();
    }

    public String getNom() {
        return infos.getNom();
    }

    @Override
    public String toString() {
        return infos.getNom();
    }

    public MatiereMeta getInfos() {
        return infos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(infos);
    }
}

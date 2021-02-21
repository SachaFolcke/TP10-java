package tp10;

public enum MatiereMeta {
    MATHS("Mathématiques", 3, 0, false),
    FR("Français", 3, 0, false),
    ANG("Anglais", 3, 0, false),
    HG("Histoire-Géographie", 3, 0, false),
    PHY("Physiques", 3, 1, false),
    SCIN("Sciences naturelles", 3, 0, false),
    ART("Arts", 3, 0, false),
    MUS("Musique", 2, 0, false),
    SPORT("Sports", 2, 0, false),
    LV("Langue vivante", 3, 1, false),
    LAT("Latin", 3, 0, true),
    GREC("Grec", 3, 0, true),
    ANGAV("Anglais avancé", 3, 0, true);

    private String nom;
    private int notesAttendues;
    private int niveauMinimum;
    private boolean option;

    MatiereMeta(String nom, int notesAttendues, int niveauMinimum, boolean option) {
        this.nom = nom;
        this.notesAttendues = notesAttendues;
        this.niveauMinimum = niveauMinimum;
        this.option = option;
    }

    public String getNom() {
        return nom;
    }

    public int getNotesAttendues() {
        return notesAttendues;
    }

    public int getNiveauMinimum() {
        return niveauMinimum;
    }

    public boolean isOption() {
        return option;
    }
}

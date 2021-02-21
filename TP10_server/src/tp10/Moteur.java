package tp10;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Moteur {

    static final int ELEVES_CLASSE = 20;
    private static Moteur Instance;

    private static List<Matiere> matieres;
    private static List<Matiere> options;
    private static List<Classe> classes;
    private static List<Epreuve> epreuves;


    /**
     * Récupération de l'instance du singleton Moteur
     * @return Instance unique Moteur
     */
    public static Moteur getInstance() {
        if(Instance == null) {
            Instance = new Moteur();
        }
        return Instance;
    }


    /**
     * Génére les objets Matieres à partir des énumérations MatiereMeta
     */
    private void setupMatieres() {
        for(MatiereMeta m : MatiereMeta.values()) {
            matieres.add(new Matiere(m));

            if(m.isOption()) {
                options.add(matieres.get(matieres.size() - 1));
            }
        }
    }

    /**
     * Pour chaque Niveau et chaque NumClasse, crée une Classe
     * Pour chaque Classe, génère ELEVES_CLASSE Eleve
     */
    private void setupClassesEtEleves() {
        Faker faker = new Faker(Locale.FRENCH);

        for(Niveau niv : Niveau.values()) {
            for(NumClasse num : NumClasse.values()) {
                classes.add(new Classe(niv, num));
            }
        }

        for(Classe c : classes) {
            for(int i = 0; i < ELEVES_CLASSE; i++) {
                Eleve e = new Eleve(faker.name().lastName(), faker.name().firstName());
                e.genererOptions(options.get(0), options.get(1), options.get(2));
                c.ajouterEleve(e);
            }
        }
    }


    /**
     * Génère selon une distribution gaussienne des épreuves et leurs notes
     * pour les Eleves concernés
     */
    private void setupEpreuves() {
        Random random = new Random();
        for (Classe classe : classes) {
            for (Matiere matiere : matieres) {
                if (classe.getNiveau().ordinal() < matiere.getNiveauMinimum()) {
                    continue;
                }

                for (int i = 0; i < matiere.getNotesAttendues(); i++) {

                    Epreuve epreuve = new Epreuve(matiere);
                    for (Eleve eleve : classe.getEleves()) {
                        if (matiere.isOption() && !eleve.getOptions().contains(matiere)) {
                            continue;
                        }
                        double note;
                        if(matiere.isOption()) {
                            note = Math.round(((random.nextGaussian() * 2.5 + 15)) * 2) / 2.0;
                        } else {
                            note = Math.round(((random.nextGaussian() * 5 + 12)) * 2) / 2.0;
                        }
                        epreuve.getNotes().put(eleve, note);
                        eleve.ajouterNote(matiere, note);

                    }
                    if (!epreuve.getNotes().isEmpty()) {
                        epreuves.add(epreuve);
                    }
                }
            }
        }
    }

    public static List<Matiere> getMatieres() {
        return matieres;
    }

    public static List<Matiere> getOptions() {
        return options;
    }

    public static List<Classe> getClasses() { return classes; }

    public static List<Epreuve> getEpreuves() {
        return epreuves;
    }

    /**
     * Récupère toutes les classes d'un Niveau
     * @param niveau Objet Niveau concerné
     * @return Une liste de Classe
     */
    public static List<Classe> getClassesNiveau(Niveau niveau) {
        List<Classe> classes = getClasses();
        List<Classe> result = new ArrayList<>();

        for(Classe classe : classes) {
            if(classe.getNiveau().equals(niveau)) {
                result.add(classe);
            }
        }

        return result;
    }

    /**
     * Constructeur singleton
     */
    private Moteur() {
        matieres = new ArrayList<>();
        options = new ArrayList<>();
        epreuves = new ArrayList<>();
        classes = new ArrayList<>();
    }

    /**
     * Fonction lançant toutes les fonctions de générations de données
     */
    public void generer() {
        Moteur moteur = Moteur.getInstance();

        moteur.setupMatieres();
        moteur.setupClassesEtEleves();
        moteur.setupEpreuves();
    }
}

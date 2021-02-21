package tp10;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Moteur {

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
    private static void setupMatieres() {
        for(MatiereMeta m : MatiereMeta.values()) {
            matieres.add(new Matiere(m));

            if(m.isOption()) {
                options.add(matieres.get(matieres.size() - 1));
            }
        }
    }


    /**
     * Renvoie l'objet Matiere ayant le même nom que celui donné
     * @param nom String nom à rechercher
     * @return Un objet Matiere ou null
     */
    public static Matiere getMatiereParNom(String nom) {
        for(Matiere m : getMatieres()) {
            if(m.getNom().equals(nom)) {
                return m;
            }
        }
        return null;
    }


    /**
     * Gère la string envoyée par le serveur et la parse en tant que JSON pour créer les objets et peupler les listes
     * @param json String formatée en JSON envoyée par le Serveur de ce TP
     */
    public static void parseJSONIntoObjects(String json) {
        if(Instance == null) {
            Instance = new Moteur();
        }

        setupMatieres();

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonOb = (JSONObject) parser.parse(json);
            for(Object classeJson : jsonOb.keySet()) {
                String niveau = classeJson.toString().split(" ")[0];
                String lettre = classeJson.toString().split(" ")[1];

                Classe classe = new Classe(Niveau.getNiveauParNom(niveau), NumClasse.getNumParNom(lettre));
                classes.add(classe);

                JSONObject eleves = ((JSONObject) jsonOb.get(classeJson));
                for(Object eleveJson : eleves.keySet()) {
                    String prenom = eleveJson.toString().split(" ")[0];
                    String nom = eleveJson.toString().split(" ")[1];
                    Eleve e = new Eleve(prenom, nom);
                    classe.ajouterEleve(e);

                    for(Object matieresJson : ((JSONObject) eleves.get(eleveJson)).keySet()) {
                        JSONArray notes = ((JSONArray) ((JSONObject) eleves.get(eleveJson)).get(matieresJson));

                        Matiere matiere = getMatiereParNom((String) matieresJson);

                        if(matiere.isOption()) {
                            e.getOptions().add(matiere);
                        }

                        for(Object n : notes) {
                            e.ajouterNote(matiere, (double) n);
                        }
                    }
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
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
     * Récupère tous les élèves d'un Niveau
     * @param niveau Objet niveau concerné
     * @return Une liste d'Eleve
     */
    public static List<Eleve> getElevesNiveau(Niveau niveau) {
        List<Classe> classes = getClassesNiveau(niveau);
        List<Eleve> eleves = new ArrayList<>();

        for(Classe c : classes){
            eleves.addAll(c.getEleves());
        }

        return eleves;
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
}

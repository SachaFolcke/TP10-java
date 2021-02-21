package tp10;

import java.util.*;

public class Traitement {

    /* Permet de récupérer toutes les notes de toutes les matières d'une seule Classe donnée
    * Renvoie une Map avec comme clé une Matiere, et comme valeur un tableau de Double (représentant
    * toutes les notes de la classe pour cette unique Matiere) */
    public static Map<Matiere, ArrayList<Double>> getAllClasseNotes(Classe classe) {
        Map<Matiere, ArrayList<Double>> result = new LinkedHashMap<>();

        for(Eleve eleve : classe.getEleves()) {
            Map<Matiere, ArrayList<Double>> bulletin = eleve.getBulletin();
            for(Matiere matiere : bulletin.keySet()) {
                if(!result.containsKey(matiere)) {
                    result.put(matiere, new ArrayList<>());
                }

                for(int i = 0; i < bulletin.get(matiere).size(); i++) {
                    result.get(matiere).add(bulletin.get(matiere).get(i));
                }
            }
        }

        return result;
    }

    /* Permet d'obtenir toutes les notes d'un élève, triées par Matiere grâce à une Map<Matiere, ArrayList<Double>> */
    public static Map<Matiere, ArrayList<Double>> getBulletin(Eleve eleve) {
        return eleve.getBulletin();
    }

    /* Met en commun toutes les notes des élèves d'un Niveau, triées par Matiere grâce à une Map<Matiere, ArrayList<Double>>  */
    public static Map<Matiere, ArrayList<Double>> getAllNiveauNotes(Niveau niveau) {
        Map<Matiere, ArrayList<Double>> result = new LinkedHashMap<>();

        List<Classe> classes = new ArrayList<>();
        for(Classe classe : Moteur.getClasses()) {
            if(classe.getNiveau().equals(niveau)) {
                classes.add(classe);
            }
        }

        for(Classe classe : classes) {
            for(Eleve eleve : classe.getEleves()) {
                Map<Matiere, ArrayList<Double>> bulletin = eleve.getBulletin();
                for(Matiere matiere : bulletin.keySet()) {
                    if(!result.containsKey(matiere)) {
                        result.put(matiere, new ArrayList<>());
                    }

                    for(int i = 0; i < bulletin.get(matiere).size(); i++) {
                        result.get(matiere).add(bulletin.get(matiere).get(i));
                    }
                }
            }
        }
        return result;
    }

    /* Permet d'obtenir la note MAXIMALE pour chaque Matiere dans une classe TOUTE EPREUVE CONFONDUE
    * Renvoie une Map sous la forme Map<Matiere, Double> où Double est la note maximale */
    public static Map<Matiere, Double> getNoteMaxClasse(Classe classe) {
        Map<Matiere, Double> result = new LinkedHashMap<>();
        Map<Matiere, ArrayList<Double>> notes = getAllClasseNotes(classe);

        for(Matiere matiere : notes.keySet()) {
            double max = Collections.max(notes.get(matiere));
            result.put(matiere, max);
        }

        return result;
    }

    /* Permet d'obtenir la note MINIMALE pour chaque Matiere dans une classe TOUTE EPREUVE CONFONDUE
    *  Renvoie une Map sous la forme Map<Matiere, Double> où Double est la note minimale */
    public static Map<Matiere, Double> getNoteMinClasse(Classe classe) {
        Map<Matiere, Double> result = new LinkedHashMap<>();
        Map<Matiere, ArrayList<Double>> notes = getAllClasseNotes(classe);

        for(Matiere matiere : notes.keySet()) {
            double min = Collections.min(notes.get(matiere));
            result.put(matiere, min);
        }

        return result;
    }

    /* Permet d'obtenir la note MOYENNE pour chaque Matiere dans une classe TOUTE EPREUVE CONFONDUE
     *  Renvoie une Map sous la forme Map<Matiere, Double> où Double est la note moyenne */
    public static Map<Matiere, Double> getNoteMoyenneClasse(Classe classe) {
        Map<Matiere, Double> result = new LinkedHashMap<>();
        Map<Matiere, ArrayList<Double>> notes = getAllClasseNotes(classe);

        for(Matiere matiere : notes.keySet()) {
            double somme = 0.0;
            for(Double note : notes.get(matiere)) {
                somme += note;
            }
            result.put(matiere, somme / notes.get(matiere).size());
        }

        return result;
    }

    /* Permet d'obtenir la note MEDIANE pour chaque Matiere dans une classe TOUTE EPREUVE CONFONDUE
     *  Renvoie une Map sous la forme Map<Matiere, Double> où Double est la note médiane */
    public static Map<Matiere, Double> getNoteMedianeClasse(Classe classe) {
        Map<Matiere, Double> result = new LinkedHashMap<>();
        Map<Matiere, ArrayList<Double>> notes = getAllClasseNotes(classe);

        for(Matiere matiere : notes.keySet()) {
            List<Double> med = new ArrayList<>(notes.get(matiere));
            Collections.sort(med);

            int milieu = med.size() / 2;

            if (med.size() % 2 == 1) {
                result.put(matiere, med.get(milieu));
            } else {
                result.put(matiere, (med.get(milieu - 1) + med.get(milieu)) / 2.0);
            }
        }

        return result;
    }

    /* Renvoie les moyennes de chaque Classe d'un Niveau concernant une Matiere
    * sous la forme d'une Map<Classe, Double> où Double est la moyenne de la-dite Classe */
    public static Map<Classe, Double> getNotesMatiereMoyenneNiveau(Matiere matiere, Niveau niveau) {
        Map<Classe, Double> moyennes = new LinkedHashMap<>();

        for(Classe classe : Moteur.getClassesNiveau(niveau)) {
            moyennes.put(classe, Traitement.getNoteMoyenneClasse(classe).get(matiere));
        }

        return moyennes;
    }

    /* Rnvoie la moyenne générale d'un élève */
    public static double getMoyenneGeneraleEleve(Eleve eleve) {
        Map<Matiere, ArrayList<Double>> bulletin = eleve.getBulletin();
        double sommeMoyennes = 0.0;

        for(Matiere m : bulletin.keySet()) {
            double somme = 0.0;

            ArrayList<Double> notes = bulletin.get(m);
            for(double note : notes) {
                somme += note;
            }

            sommeMoyennes += somme / notes.size();
        }

        return sommeMoyennes / bulletin.size();
    }
}

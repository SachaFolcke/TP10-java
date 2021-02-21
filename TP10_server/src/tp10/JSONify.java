package tp10;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONify {

    private FileWriter writer;

    /**
     * A partir d'une liste de Classe, génère un JSON ayant pour achitecture :
     * Classe > Eleve > Matiere > double[] (notes)
     * @param classes Une liste de Classe à exporter
     * @return JSONObject
     */
    public JSONObject generateJSONExport(List<Classe> classes) {
        JSONObject json = new JSONObject();

        for(Classe classe : classes) {
            JSONObject jsonClasse = new JSONObject();

            for(Eleve eleve : classe.getEleves()) {
                JSONObject jsonEleve = new JSONObject();

                for(Matiere matiere : eleve.getBulletin().keySet()) {
                    JSONArray jsonNotes = new JSONArray();

                    for(Double note : eleve.getBulletin().get(matiere)) {
                        jsonNotes.add(note);
                    }
                    jsonEleve.put(matiere, jsonNotes);
                }
                jsonClasse.put(eleve, jsonEleve);
            }
            json.put(classe, jsonClasse);
        }


        try {
            writer = new FileWriter("output.json");
            writer.write(json.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}

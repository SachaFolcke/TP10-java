package tp10;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {

    /**
     * Envoie un JSONObject en tant que string dans un socket
     * @param json JSONObject concerné
     * @param port Numéro de port concerné
     * @throws IOException Erreur d'écriture
     */
    public void sendJson(JSONObject json, int port) throws IOException {
        Socket socket = new Socket("127.0.0.1", port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(json.toJSONString());
    }
}

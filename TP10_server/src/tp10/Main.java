package tp10;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static int PORT_NUMBER = 8123; // Numéro de port
    private Socket socket; // Socket à utiliser
    private static JSONObject json; // Objet JSON à envoyer

    private Main(Socket socket) {
        this.socket = socket;
        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
        run();
    }

    /**
     * Envoi des données lorsqu'un client se connecte
     */
    public void run() {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            while ((br.readLine()) != null) {
                out.write(json.toJSONString().getBytes());
                break;
            }

        } catch (IOException ex) {
            System.out.println("Impossible de lire les requêtes des clients");

        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Génération de nouvelles données...");
        Moteur moteur = Moteur.getInstance();
        moteur.generer();

        System.out.println("Export dans \"output.json\"...");
        JSONify jsonify = new JSONify();
        json = jsonify.generateJSONExport(Moteur.getClasses());

        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT_NUMBER);
            while (true) {
                System.out.println("Serveur démarré : prêt à recevoir des connexions");
                new Main(server.accept());
            }
        } catch (IOException ex) {
            System.out.println("Impossible de démarrer le serveur.");
        } finally {
            try {
                if (server != null) {
                    server.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

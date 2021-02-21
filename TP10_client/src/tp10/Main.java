package tp10;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller.setupInterface(primaryStage);
    }


    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 8123;

        try {
            String serverHostname = host;

            System.out.println("Connexion au serveur " + serverHostname + " sur le port " + port + ".");

            Socket echoSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                echoSocket = new Socket(serverHostname, port);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            }
            catch (UnknownHostException e) {
                System.err.println("Hôte inconnu : " + serverHostname);
                System.exit(1);
            }
            catch (IOException e) {
                System.err.println("Impossible de se connecter au serveur");
                System.exit(1);
            }
            out.println("json");

            Moteur.parseJSONIntoObjects(in.readLine());

            /** Closing all the resources */
            out.close();
            in.close();
            echoSocket.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        launch(args);
    }
}

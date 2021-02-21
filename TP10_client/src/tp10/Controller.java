package tp10;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller {

    static Graphs graphs;
    static TabPane onglets;
    static Tab tab1, tab2, tab3, tab4;
    static GridPane grid1, grid2, grid3, grid4;
    static ComboBox<Niveau> comboBoxNiveau, comboBoxNiveau2, comboBoxNiveau3, comboBoxNiveau4;
    static ComboBox<Matiere> comboBoxMatiere, comboBoxMatiere2;
    static ComboBox comboBoxNumEpr;
    static ComboBox<Classe> comboBoxClasse;
    static ComboBox<Eleve> comboBoxEleve;


    /**
     * Instanciation des éléments graphiques de l'onglet n°1
     */
    public static void setupOnglet1() {

        tab1 = new Tab("Moyennes Matiere/Niveau");
        tab1.closableProperty().set(false);
        onglets.getTabs().add(tab1);

        grid1 = new GridPane();

        Matiere matiere = Moteur.getMatieres().get(0);
        graphs.updateBCMoyNiv(matiere, Niveau.SIXIEME);

        comboBoxNiveau = new ComboBox<Niveau>();
        for(Niveau niveau : Niveau.values()) {
            comboBoxNiveau.getItems().add(niveau);
        }
        comboBoxNiveau.getSelectionModel().selectFirst();

        comboBoxMatiere = new ComboBox<>();
        for(Matiere mat : Moteur.getMatieres()) {
            comboBoxMatiere.getItems().add(mat);
        }
        comboBoxMatiere.getSelectionModel().selectFirst();

        ChangeListener listenerCombo = new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        graphs.updateBCMoyNiv(
                                comboBoxMatiere.getValue(),
                                comboBoxNiveau.getValue());
                    }
                };

        comboBoxNiveau.valueProperty().addListener(listenerCombo);
        comboBoxMatiere.valueProperty().addListener(listenerCombo);

        grid1.add(comboBoxNiveau, 0, 0);
        grid1.add(comboBoxMatiere, 1, 0);
        grid1.add(graphs.getBCMoyNiv(), 0, 1, 5, 1);

        tab1.setContent(grid1);
    }


    /**
     * Instanciation des éléments graphiques de l'onglet n°2
     */
    public static void setupOnglet2() {

        tab2 = new Tab("Répartition notes épreuves");
        tab2.closableProperty().set(false);
        onglets.getTabs().add(tab2);

        grid2 = new GridPane();

        Matiere matiere = Moteur.getMatieres().get(0);
        graphs.updateLCRepEpr(matiere, Niveau.SIXIEME, 1);

        comboBoxNiveau2 = new ComboBox<Niveau>();
        for(Niveau niveau : Niveau.values()) {
            comboBoxNiveau2.getItems().add(niveau);
        }
        comboBoxNiveau2.getSelectionModel().selectFirst();

        comboBoxMatiere2 = new ComboBox<>();
        for(Matiere mat : Moteur.getMatieres()) {
            comboBoxMatiere2.getItems().add(mat);
        }
        comboBoxMatiere2.getSelectionModel().selectFirst();

        comboBoxNumEpr = new ComboBox<>();
        comboBoxNumEpr.getItems().addAll(1, 2, 3, "Moyenne");
        comboBoxNumEpr.getSelectionModel().selectFirst();

        ChangeListener listenerCombo2 = new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        graphs.updateLCRepEpr(
                                comboBoxMatiere2.getValue(),
                                comboBoxNiveau2.getValue(),
                                (int) (comboBoxNumEpr.getValue() == "Moyenne" ? -1 : comboBoxNumEpr.getValue()));
                    }
                };

        comboBoxNiveau2.valueProperty().addListener(listenerCombo2);
        comboBoxMatiere2.valueProperty().addListener(listenerCombo2);
        comboBoxNumEpr.valueProperty().addListener(listenerCombo2);

        grid2.add(comboBoxNiveau2, 0, 0);
        grid2.add(comboBoxMatiere2, 1, 0);
        grid2.add(comboBoxNumEpr, 2, 0);
        grid2.add(graphs.getLCRepEpr(), 0 ,1, 5, 1);

        tab2.setContent(grid2);
    }


    /**
     * Instanciation des éléments graphiques de l'onglet n°3
     */
    public static void setupOnglet3() {

        tab3 = new Tab("Répartion moyennes par niveau");
        tab3.closableProperty().set(false);
        onglets.getTabs().add(tab3);

        grid3 = new GridPane();

        graphs.updatePCMoyNiv(Niveau.SIXIEME);

        comboBoxNiveau3 = new ComboBox<Niveau>();
        for(Niveau niveau : Niveau.values()) {
            comboBoxNiveau3.getItems().add(niveau);
        }
        comboBoxNiveau3.getSelectionModel().selectFirst();

        ChangeListener listenerCombo3 = new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object o, Object t1) {
                        graphs.updatePCMoyNiv(comboBoxNiveau3.getValue());
                    }
                };

        comboBoxNiveau3.valueProperty().addListener(listenerCombo3);

        grid3.add(comboBoxNiveau3, 0, 0);
        grid3.add(graphs.getPCMoyNiv(), 0, 1, 10, 1);

        tab3.setContent(grid3);
    }

    /**
     * Instanciation des éléments graphiques de l'onglet n°3
     */
    public static void setupOnglet4() {
        tab4 = new Tab("Bulletins");
        tab4.closableProperty().set(false);
        onglets.getTabs().add(tab4);

        grid4 = new GridPane();

        graphs.updateTableBulletins(null);

        comboBoxNiveau4 = new ComboBox<Niveau>();
        for(Niveau niveau : Niveau.values()) {
            comboBoxNiveau4.getItems().add(niveau);
        }
        comboBoxNiveau4.getSelectionModel().selectFirst();

        comboBoxClasse = new ComboBox<Classe>();
        comboBoxEleve = new ComboBox<Eleve>();

        ChangeListener comboBoxNiveauListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                comboBoxClasse.getItems().clear();
                comboBoxEleve.getItems().clear();
                comboBoxEleve.setDisable(true);
                for(Classe classe : Moteur.getClassesNiveau(comboBoxNiveau4.getValue())) {
                    comboBoxClasse.getItems().add(classe);
                }
            }
        };

        comboBoxNiveau4.valueProperty().addListener(comboBoxNiveauListener);

        ChangeListener comboBoxClasseListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                comboBoxEleve.getItems().clear();
                comboBoxEleve.setDisable(false);
                try {
                    for (Eleve eleve : comboBoxClasse.getValue().getEleves()) {
                        comboBoxEleve.getItems().add(eleve);
                    }
                    comboBoxEleve.getSelectionModel().selectFirst();
                } catch (NullPointerException ignored) {}
            }
        };

        comboBoxClasse.valueProperty().addListener(comboBoxClasseListener);

        ChangeListener comboBoxEleveListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if(comboBoxClasse.getValue() == null) {
                    comboBoxEleve.setDisable(true);
                } else {
                    graphs.updateTableBulletins(comboBoxEleve.getValue());
                }
            }
        };

        comboBoxEleve.valueProperty().addListener(comboBoxEleveListener);

        grid4.add(comboBoxNiveau4, 0, 0);
        grid4.add(comboBoxClasse, 1, 0);
        grid4.add(comboBoxEleve, 2, 0);
        grid4.add(graphs.getTableBulletins(), 0, 1, 10, 10);

        tab4.setContent(grid4);
    }

    /**
     * Initialise tous les composants graphique de l'application, et puis l'ajoute au Stage passé en paramètre
     * @param primaryStage Stage dans lequel ajouter les composants graphiques
     */
    public static void setupInterface(Stage primaryStage) {

        onglets = new TabPane();
        graphs = new Graphs();

        setupOnglet1();
        setupOnglet2();
        setupOnglet3();
        setupOnglet4();

        Scene scene = new Scene(onglets,800,550);

        primaryStage.setTitle("Visualisation des notes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

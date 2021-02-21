package tp10;

import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Graphs {

    private BarChart<String, Number> BCMoyNiv; // BarChart Moyennes par Niveau et par matière
    private StackedAreaChart<Number, Number> LCRepEpr; // LineChart Répartition notes par Épreuve
    private PieChart PCMoyNiv; // PieChart Moyenne générale par niveau
    private TableView tableBulletins;


    /**
     * Setup le BarChart représentant les moyennes par niveau et par matière
     */
    public void setupBCMoyNiv() {
        if (this.BCMoyNiv == null) {
            final Axis<String> xAxis = new CategoryAxis();
            final Axis<Number> yAxis = new NumberAxis();
            this.BCMoyNiv = new BarChart<String, Number>(xAxis, yAxis);
            this.BCMoyNiv.setTitle("Moyennes");
            xAxis.setLabel("Classe");
            yAxis.setLabel("Note");
            this.BCMoyNiv.setPrefWidth(800);
            this.BCMoyNiv.setPrefHeight(800);
        }
    }


    /**
     * Met à jour et affiche les valeurs dans le BarChart selon une Matiere et un Niveau donné
     * @param matiere Objet Matiere concerné
     * @param niveau Objet Niveau concerné
     */
    public void updateBCMoyNiv(Matiere matiere, Niveau niveau) {
        if (this.BCMoyNiv == null) {
            setupBCMoyNiv();
        }

        this.BCMoyNiv.getData().clear();

        Map<Classe, Double> moyennes = Traitement.getNotesMatiereMoyenneNiveau(matiere, niveau);
        Map<Classe, Double> moyennesCopie = new LinkedHashMap<>(moyennes);

        for (Classe classe : moyennesCopie.keySet()) {
            if (moyennes.get(classe) == null) {
                moyennes.remove(classe);
            }
        }

        if (moyennes.isEmpty()) {
            return;
        }

        XYChart.Series serie = new XYChart.Series();
        serie.setName(matiere.getNom());
        for (Classe classe : moyennes.keySet()) {
            serie.getData().add(new XYChart.Data(classe.toString(), moyennes.get(classe)));
        }
        this.BCMoyNiv.getData().addAll(serie);
    }

    public BarChart<String, Number> getBCMoyNiv() {
        return BCMoyNiv;
    }


    /**
     * Setup le LineChart représentant la répartition des notes par épreuve
     */
    public void setupLCRepEpr() {
        if (this.LCRepEpr == null) {
            final Axis<Number> xAxis = new NumberAxis(0, 20, 1);
            final Axis<Number> yAxis = new NumberAxis(0, 15, 1);
            this.LCRepEpr = new StackedAreaChart<>(xAxis, yAxis);
            this.LCRepEpr.setTitle("Répartition des notes");
            xAxis.setLabel("Note");
            yAxis.setLabel("Occurrences");
            this.LCRepEpr.setPrefWidth(800);
            this.LCRepEpr.setPrefHeight(800);
        }
    }


    /**
     * Met à jour et affiche les valeurs dans le LineChart selon une Matiere, un Niveau donné, et le numéro de l'épreuve
     * @param matiere Objet Matiere concerné
     * @param niveau Objet Niveau concerné
     * @param numEpreuve entier représentant le numéro de l'épreuve concernée, ou -1 pour la moyenne
     */
    public void updateLCRepEpr(Matiere matiere, Niveau niveau, int numEpreuve) {
        if (this.LCRepEpr == null) {
            setupLCRepEpr();
        }

        this.LCRepEpr.getData().clear();

        List<Classe> classes = Moteur.getClassesNiveau(niveau);
        Map<Classe, Map<Double, Integer>> resultats = new LinkedHashMap<>();


        for (Classe c : classes) {
            resultats.put(c, new LinkedHashMap<>());

            if(matiere.getNiveauMinimum() > c.getNiveau().ordinal()) {
                continue;
            }
            for (Eleve e : c.getEleves()) {

                if (matiere.isOption() && !e.getOptions().contains(matiere)) {
                    continue;
                }

                double note = 0;

                if (numEpreuve > 0) {
                    note = e.getBulletin().get(matiere).get(numEpreuve - 1);
                } else {
                    for (Double n : e.getBulletin().get(matiere)) {
                        note += n;
                    }
                    note /= e.getBulletin().get(matiere).size();
                }

                if (!resultats.get(c).containsKey(note)) {
                    resultats.get(c).put(note, 1);
                } else {
                    resultats.get(c).put(note, resultats.get(c).get(note) + 1);
                }

            }
        }


        for (Classe c : resultats.keySet()) {

            if(resultats.get(c).isEmpty()) {
                continue;
            }

            XYChart.Series series = new XYChart.Series();
            series.setName(c.toString());

            for (Double note : resultats.get(c).keySet()) {
                series.getData().add(new XYChart.Data(note, resultats.get(c).get(note)));
            }
            this.LCRepEpr.getData().addAll(series);
        }
    }

    public StackedAreaChart<Number, Number> getLCRepEpr() {
        return LCRepEpr;
    }


    /**
     * Setup le BarChart représentant la répartition des moyennes par niveau
     */
    public void setupPCMoyNiv() {
        if (this.PCMoyNiv == null) {
            this.PCMoyNiv = new PieChart();
            this.PCMoyNiv.setLegendSide(Side.LEFT);
            this.PCMoyNiv.setPrefWidth(900);
            this.PCMoyNiv.setPrefHeight(900);
        }
    }

    /**
     * Met à jour et affiche les valeurs dans le PieChart selon un Niveau donné
     * @param niveau Objet Niveau concerné
     */
    public void updatePCMoyNiv(Niveau niveau) {
        if(this.PCMoyNiv == null) {
            setupPCMoyNiv();
        }

        this.PCMoyNiv.getData().clear();

        ArrayList<Eleve> eleves = (ArrayList<Eleve>) Moteur.getElevesNiveau(niveau);
        List<Double> moyennes = new ArrayList<>();

        for (Eleve e : eleves) {
            moyennes.add(Traitement.getMoyenneGeneraleEleve(e));
        }

        int insuffisant = 0;
        int passable = 0;
        int assezBien = 0;
        int bien = 0;
        int tresBien = 0;

        for (double note : moyennes) {
            if ((note < 10)) {
                insuffisant++;
            } else if (note < 12) {
                passable++;
            } else if (note < 14) {
                assezBien++;
            } else if (note < 16) {
                bien++;
            } else {
                tresBien++;
            }
        }

        PieChart.Data slice1 = new PieChart.Data("Insuffisant : " + insuffisant + "\n(x < 10)", insuffisant);
        PieChart.Data slice2 = new PieChart.Data("Passable : " + passable + "\n(10 <= x < 12)", passable);
        PieChart.Data slice3 = new PieChart.Data("Assez bien : " + assezBien + "\n(12 <= x < 14)", assezBien);
        PieChart.Data slice4 = new PieChart.Data("Bien : " + bien + "\n(14 <= x < 16)", bien);
        PieChart.Data slice5 = new PieChart.Data("Très bien : " + tresBien + "\n(16 < x)", tresBien);

        this.PCMoyNiv.getData().add(slice1);
        this.PCMoyNiv.getData().add(slice2);
        this.PCMoyNiv.getData().add(slice3);
        this.PCMoyNiv.getData().add(slice4);
        this.PCMoyNiv.getData().add(slice5);
    }

    public PieChart getPCMoyNiv() {
        return PCMoyNiv;
    }

    /**
     * Setup la Table affichant le bulletin d'un seul Eleve (à sélectionner)
     */
    public void setupTableBulletins() {
        if(this.tableBulletins == null) {
            this.tableBulletins = new TableView();
            TableColumn<BulletinView, String> matiereCol = new TableColumn<>("Matiere");
            matiereCol.setCellValueFactory(new PropertyValueFactory<>("matiere"));
            matiereCol.setPrefWidth(150);

            TableColumn<BulletinView, Double> note1Col = new TableColumn<>("1");
            note1Col.setCellValueFactory(new PropertyValueFactory<>("note1"));

            TableColumn<BulletinView, Double> note2Col = new TableColumn<>("2");
            note2Col.setCellValueFactory(new PropertyValueFactory<>("note2"));

            TableColumn<BulletinView, Double> note3Col = new TableColumn<>("3");
            note3Col.setCellValueFactory(new PropertyValueFactory<>("note3"));

            TableColumn<BulletinView, Double> moyenneCol = new TableColumn<>("Moyenne");
            moyenneCol.setCellValueFactory(new PropertyValueFactory<>("moyenne"));

            this.tableBulletins.getColumns().add(matiereCol);
            this.tableBulletins.getColumns().add(note1Col);
            this.tableBulletins.getColumns().add(note2Col);
            this.tableBulletins.getColumns().add(note3Col);
            this.tableBulletins.getColumns().add(moyenneCol);

            this.tableBulletins.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            this.tableBulletins.setPrefWidth(1600);
            this.tableBulletins.setPrefHeight(1600);
        }
    }

    /**
     * Met à jour et affiche les valeurs dans la Table selon un Eleve donné
     * @param eleve Objet Eleve concerné
     */
    public void updateTableBulletins(Eleve eleve) {
        if(this.tableBulletins == null) {
            setupTableBulletins();
        }

        tableBulletins.getItems().clear();

        if(eleve == null) {
            return;
        }

        Map<Matiere, ArrayList<Double>> bulletin = eleve.getBulletin();
        for(Matiere matiere : bulletin.keySet()) {
            tableBulletins.getItems().add(new BulletinView(matiere, bulletin.get(matiere)));
        }
    }

    public TableView getTableBulletins() {
        return tableBulletins;
    }

    public Graphs() {
    }
}

package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Controller {
    @FXML
    AnchorPane root;
    @FXML
    TabPane first;
    @FXML
    TabPane second;
    @FXML
    BarChart barChart;
    @FXML
    ChoiceBox<String> chartType;

    final String defaultPath = "./config.yml";
    Yaml yaml;

    String selectedType;
    File dirRoot;
    List<Integer> intervals;
    List<String> intervalNames;
    Stage stage;
    Counter counter;
    MyBarChart genreChart;
    List<MySecondChart> mySecondCharts;
    Map<String, Supplier<MySecondChart>> supplierMap;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Controller() {
        mySecondCharts = new ArrayList<>();
        supplierMap = new HashMap<>();
        supplierMap.put("PieChart",MyPieChart::new);
        supplierMap.put("StackedBarChart",MyStackedBarChart::new);
    }

    private void redraw() {
        try {
            counter = new Counter(dirRoot, intervals,intervalNames);
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"The root is not a directory").showAndWait();
            e.printStackTrace();
            return;
        } catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR, "The config is wrong!").showAndWait();
            e.printStackTrace();
            return;
        }
        mySecondCharts.clear();
        second.getTabs().clear();
        genreChart = new MyBarChart(barChart);
        List<Counter.GenreEntity> list = counter.getGenreEntities();
        genreChart.init(list);
        for (Counter.GenreEntity genreEntity : list) {
            Tab tab = new Tab();
            MySecondChart mySecondChart = supplierMap.get(selectedType).get();
            mySecondCharts.add(mySecondChart);
            mySecondChart.init(genreEntity.getName(), counter.getIntervalEntitiesByGenre(genreEntity.getName()));
            Chart chart = mySecondChart.getChart();
            chart.prefHeightProperty().bind(second.heightProperty());
            chart.prefWidthProperty().bind(second.widthProperty());
            tab.setContent(chart);
            tab.setText(genreEntity.getName());
            second.getTabs().add(tab);
        }
    }

    @FXML
    public void setConfig(){
        loadConfig(chooseFile(),true,true);
        redraw();
    }

    @FXML
    public void setRoot() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        this.dirRoot = directoryChooser.showDialog(stage);
        redraw();
    }

    @FXML
    public void setIntervals() {
        loadConfig(chooseFile(),false,true);
        redraw();
    }

    private File chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("config file","*.yml"));
        return fileChooser.showOpenDialog(stage);
    }

    public void init() {
        first.prefWidthProperty().bind(root.widthProperty().subtract(100));
        first.prefHeightProperty().bind(root.heightProperty());
        barChart.prefWidthProperty().bind(first.widthProperty());
        barChart.prefHeightProperty().bind(first.heightProperty().subtract(50));
        yaml = new Yaml();
        loadConfig(new File(defaultPath), true, true);
        chartType.getItems().addAll("PieChart", "StackedBarChart");
        chartType.getSelectionModel().selectedItemProperty().addListener((v,ov,nv)->{
            Controller.this.selectedType = nv;
            redraw();
        });
        chartType.getSelectionModel().select(0);
    }

    @SuppressWarnings("unchecked")
    public void loadConfig(File file, boolean loadRoot, boolean loadIntervals) {
        if(file==null) return;
        try (BufferedReader yamlReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8.name()))) {
            Map<String,Object> configMap = yaml.load(yamlReader);
            if (loadRoot)
                this.dirRoot = new File((String) configMap.get("Root"));
            if (loadIntervals) {
                this.intervals = (List<Integer>) configMap.get("Intervals");
                this.intervalNames = (List<String>) configMap.get("IntervalNames");
            }
        } catch (FileNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,"Config file doesn't exist.").showAndWait();
            e.printStackTrace();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"IO Exception happened.").showAndWait();
            e.printStackTrace();
        }
    }

}

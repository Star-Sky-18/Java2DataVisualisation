package sample;

import javafx.scene.chart.*;

import java.util.List;

public class MyBarChart{
    private BarChart barChart;

    MyBarChart(){
        this(new BarChart(new CategoryAxis(),new NumberAxis()));
    }

    MyBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    void init(List<Counter.GenreEntity> list) {
        barChart.getData().clear();
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        barChart.setTitle("File quantity statistics");
        xAxis.setLabel("File category");
        yAxis.setLabel("File count");
        for (Counter.GenreEntity genreEntity : list) {
            XYChart.Series<String, Integer> genreCounter = new XYChart.Series<>();
            genreCounter.setName(genreEntity.getName());
            genreCounter.getData().add(new XYChart.Data<>(""
                    , genreEntity.getCount()));
            barChart.getData().add(genreCounter);
        }
    }
}

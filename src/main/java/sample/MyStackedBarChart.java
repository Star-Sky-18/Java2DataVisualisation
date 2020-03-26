package sample;

import javafx.scene.chart.*;

import java.util.List;

public class MyStackedBarChart extends MySecondChart{
    StackedBarChart stackedBarChart;
    CategoryAxis xAxis;
    NumberAxis yAxis;
    public MyStackedBarChart(){
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.stackedBarChart = new StackedBarChart(xAxis,yAxis);
    }

    @Override
    public void init(String title, List<Counter.IntervalEntity> list) {
        xAxis.setLabel("");
        yAxis.setLabel("file count");
        for(Counter.IntervalEntity intervalEntity:list) {
            if(intervalEntity.getRatio()==0) continue;
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data<>("",intervalEntity.getRatio()));
            series.setName(intervalEntity.getName());
            stackedBarChart.getData().add(series);
        }
    }

    @Override
    public Chart getChart() {
        return stackedBarChart;
    }
}

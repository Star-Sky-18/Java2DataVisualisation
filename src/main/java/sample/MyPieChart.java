package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;

import java.util.List;

public class MyPieChart extends MySecondChart{
    private PieChart pieChart;
    private ObservableList<PieChart.Data> data;
    public MyPieChart(){
        this.pieChart = new PieChart();
        data = FXCollections.observableArrayList();
    }

    @Override
    public Chart getChart(){
        return pieChart;
    }

    public void init(String title, List<Counter.IntervalEntity> list){
        for (Counter.IntervalEntity intervalEntity : list) {
            if(intervalEntity.getRatio()==0) continue;
            data.add(new PieChart.Data(intervalEntity.getName(), intervalEntity.getRatio()));
        }
        pieChart.setData(data);
        pieChart.setTitle(title);
    }
}

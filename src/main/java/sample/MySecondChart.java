package sample;

import javafx.scene.chart.Chart;

import java.util.List;

public abstract class MySecondChart {
    public abstract void init(String title, List<Counter.IntervalEntity> list);

    public abstract Chart getChart();
}

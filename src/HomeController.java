import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public AnchorPane homeIndexPane;

    @FXML
    private PieChart demographicsPieChart;

    @FXML
    private LineChart<String, Number> populationLineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("0-18 years", 250),
                new PieChart.Data("19-35 years", 450),
                new PieChart.Data("36-50 years", 300),
                new PieChart.Data("51+ years", 200)
        );
        demographicsPieChart.setData(pieChartData);
        demographicsPieChart.setTitle("Age Demographics");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Population Growth");
        series.getData().add(new XYChart.Data<>("2020", 1200));
        series.getData().add(new XYChart.Data<>("2021", 1350));
        series.getData().add(new XYChart.Data<>("2022", 1500));
        series.getData().add(new XYChart.Data<>("2023", 1650));
        series.getData().add(new XYChart.Data<>("2024", 1800));

        populationLineChart.getData().add(series);
        populationLineChart.setTitle("Annual Population Trend");
    }
}
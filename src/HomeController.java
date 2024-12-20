import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public AnchorPane homeIndexPane;
    public Label certificatesCount;
    public Label blottersCount;
    public Label residentsCount;

    @FXML
    private PieChart demographicsPieChart;

    @FXML
    private LineChart<String, Number> populationLineChart;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/barangay_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDemographicsData();
        loadPopulationTrendData();
        loadTotalCounts();
    }

    private void loadTotalCounts() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String residentsQuery = "SELECT COUNT(*) as total FROM residents";
            try (PreparedStatement pstmt = conn.prepareStatement(residentsQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    residentsCount.setText(String.valueOf(rs.getInt("total")));
                }
            }

            String blottersQuery = "SELECT COUNT(*) as total FROM blotters";
            try (PreparedStatement pstmt = conn.prepareStatement(blottersQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    blottersCount.setText(String.valueOf(rs.getInt("total")));
                }
            }

            String certificatesQuery = "SELECT COUNT(*) as total FROM barangay_certificates";
            try (PreparedStatement pstmt = conn.prepareStatement(certificatesQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    certificatesCount.setText(String.valueOf(rs.getInt("total")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle errors appropriately
            residentsCount.setText("Error");
            blottersCount.setText("Error");
            certificatesCount.setText("Error");
        }
    }

    private void loadDemographicsData() {
        String query = """
            SELECT 
                CASE 
                    WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 0 AND 18 THEN '0-18 years'
                    WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 19 AND 35 THEN '19-35 years'
                    WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 36 AND 50 THEN '36-50 years'
                    ELSE '51+ years'
                END as age_group,
                COUNT(*) as count
            FROM residents
            GROUP BY 
                CASE 
                    WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 0 AND 18 THEN '0-18 years'
                    WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 19 AND 35 THEN '19-35 years'
                    WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 36 AND 50 THEN '36-50 years'
                    ELSE '51+ years'
                END
            ORDER BY 
                CASE age_group
                    WHEN '0-18 years' THEN 1
                    WHEN '19-35 years' THEN 2
                    WHEN '36-50 years' THEN 3
                    ELSE 4
                END
        """;

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String ageGroup = rs.getString("age_group");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(ageGroup, count));
            }

            demographicsPieChart.setData(pieChartData);
            demographicsPieChart.setTitle("Age Demographics");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPopulationTrendData() {
        String query = """
            SELECT 
                YEAR(CURDATE()) - number as year,
                (
                    SELECT COUNT(*) 
                    FROM residents 
                    WHERE YEAR(date_of_birth) <= YEAR(CURDATE()) - number
                ) as population
            FROM (
                SELECT 0 as number
                UNION SELECT 1
                UNION SELECT 2
                UNION SELECT 3
                UNION SELECT 4
            ) years
            ORDER BY year
        """;

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Population Growth");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String year = String.valueOf(rs.getInt("year"));
                int population = rs.getInt("population");
                series.getData().add(new XYChart.Data<>(year, population));
            }

            populationLineChart.getData().clear();
            populationLineChart.getData().add(series);
            populationLineChart.setTitle("Annual Population Trend");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
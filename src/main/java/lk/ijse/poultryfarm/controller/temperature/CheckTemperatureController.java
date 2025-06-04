package lk.ijse.poultryfarm.controller.temperature;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.chart.XYChart;
import lk.ijse.poultryfarm.database.DBConnection;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CheckTemperatureController implements Initializable {
    public JFXComboBox<String> searchRoom;
    public JFXButton btnCheck;
    public Label lblTemperatureNow;
    public Label lblTemperatureStatus;
    public PieChart chickPieChart;
    public BarChart<String,Number> chicksDeadBarChart;

    public void searchRoomOnAction(ActionEvent actionEvent) {

    }

//    public void loadChicksDeadBarChart() {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//
//            String query = """
//            SELECT cb.batch_id, COALESCE(SUM(cs.chicks_dead), 0) AS total_dead
//            FROM chick_batch cb
//            LEFT JOIN chick_status cs ON cb.batch_id = cs.batch_id
//            GROUP BY cb.batch_id
//            ORDER BY cb.date DESC
//            LIMIT 5
//            """;
//
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet resultSet = statement.executeQuery();
//
//            Map<String, Integer> dataMap = new LinkedHashMap<>();
//
//            while (resultSet.next()) {
//                String batchId = resultSet.getString("batch_id");
//                int dead = resultSet.getInt("total_dead");
//                dataMap.put(batchId, dead);
//            }
//
//            XYChart.Series<String, Number> series = new XYChart.Series<>();
//            series.setName("Chicks Dead");
//
//            // Optional: custom colors for bars
//            String[] colors = {"#e74c3c", "#f39c12", "#3498db", "#2ecc71", "#9b59b6"};
//            int colorIndex = 0;
//
//            chicksDeadBarChart.getData().clear();
//            chicksDeadBarChart.getData().add(series);
//
//            for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
//                XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
//                series.getData().add(data);
//
//                final String color = colors[colorIndex % colors.length];
//                colorIndex++;
//
//                // Use a listener to style each bar once Node is available
//                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
//                    if (newNode != null) {
//                        newNode.setStyle("-fx-bar-fill: " + color + ";");
//
//                        // Animation: fade-in effect
//                        FadeTransition ft = new FadeTransition(Duration.millis(800), newNode);
//                        ft.setFromValue(0);
//                        ft.setToValue(1);
//                        ft.play();
//                    }
//                });
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void loadPieChart(String batchId) {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//
//            String queryTotal = "SELECT chick_total FROM chick_batch WHERE batch_id = ?";
//            String queryDead = "SELECT SUM(chicks_dead) FROM chick_status WHERE batch_id = ?";
//
//            PreparedStatement pstTotal = connection.prepareStatement(queryTotal);
//            pstTotal.setString(1, batchId);
//            ResultSet rsTotal = pstTotal.executeQuery();
//
//            int total = 0;
//            if (rsTotal.next()) total = rsTotal.getInt(1);
//
//            PreparedStatement pstDead = connection.prepareStatement(queryDead);
//            pstDead.setString(1, batchId);
//            ResultSet rsDead = pstDead.executeQuery();
//
//            int dead = 0;
//            if (rsDead.next()) dead = rsDead.getInt(1);
//
//            int alive = total - dead;
//            double deadPercent = total == 0 ? 0 : ((double) dead / total) * 100;
//            double alivePercent = total == 0 ? 0 : ((double) alive / total) * 100;
//
//            // Format labels with percentages
//            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
//                    new PieChart.Data(String.format("Alive (%.1f%%)", alivePercent), alive),
//                    new PieChart.Data(String.format("Dead (%.1f%%)", deadPercent), dead)
//            );
//
//            chickPieChart.setData(pieData);
//            chickPieChart.setTitle("Chick Mortality Overview");
//            chickPieChart.setLegendVisible(true);
//            chickPieChart.setLabelsVisible(true);
//
//            // Custom colors
//            String[] colors = {"#2ecc71", "#e74c3c"}; // Alive = green, Dead = red
//            for (int i = 0; i < pieData.size(); i++) {
//                PieChart.Data data = pieData.get(i);
//                final String color = colors[i % colors.length];
//
//                // Listener for node (after chart renders)
//                data.nodeProperty().addListener((obs, oldNode, newNode) -> {
//                    if (newNode != null) {
//                        newNode.setStyle("-fx-pie-color: " + color + ";");
//                    }
//                });
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private DoubleProperty sharedTemperature = new SimpleDoubleProperty(30.0);

    public void checkTemperatureOnAction(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/temperature/SimulateTemperature.fxml"));
            Parent root = loader.load();

            SimulateTemperatureController controller = loader.getController();
            controller.setTemperatureProperty(sharedTemperature);

            Stage stage = new Stage();
            stage.setTitle("Adjust Temperature");
            stage.setScene(new Scene(root));

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.initOwner(currentStage);

            currentStage.setOnHidden(event -> stage.close());
            currentStage.setOnCloseRequest(event -> stage.close());

            stage.show();

            lblTemperatureNow.textProperty().bind(sharedTemperature.asString("%.1f°C"));

            sharedTemperature.addListener((observable, oldValue, newValue) -> {
                double temp = newValue.doubleValue();

                if (temp < 25.0) {
                    lblTemperatureStatus.setText("Low");
                    lblTemperatureStatus.setStyle("-fx-text-fill: red");
                } else if (temp < 35) {
                    lblTemperatureStatus.setText("Normal");
                    lblTemperatureStatus.setStyle("-fx-text-fill: green");
                } else {
                    lblTemperatureStatus.setText("High");
                    lblTemperatureStatus.setStyle("-fx-text-fill: orange");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in temperature simulation window").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchRoom.getItems().addAll("Room 1","Room 2","Room 3","Room 4","Room 5");
        ButtonScale.buttonScaling(btnCheck);

//        loadChicksDeadBarChart();
//        loadPieChart("Chick Mortality Overview");
    }
}

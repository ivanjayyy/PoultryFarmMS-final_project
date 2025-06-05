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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckTemperatureController implements Initializable {
    public JFXButton btnCheck;
    public Label lblTemperatureNow;
    public Label lblTemperatureStatus;
    public JFXComboBox<String> stageSelector;

    private double min;
    private double max;

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

                if (temp < min) {
                    lblTemperatureStatus.setText("Too Low");
                    lblTemperatureStatus.setStyle("-fx-text-fill: red");
                } else if (temp < max) {
                    lblTemperatureStatus.setText("Normal");
                    lblTemperatureStatus.setStyle("-fx-text-fill: green");
                } else {
                    lblTemperatureStatus.setText("Too High");
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
        stageSelector.getItems().addAll("Stage 01","Stage 02","Stage 03");
        ButtonScale.buttonScaling(btnCheck);
        btnCheck.setDisable(true);
    }

    public void selectStageOnAction(ActionEvent actionEvent) {
        btnCheck.setDisable(false);
        String batchStage = stageSelector.getSelectionModel().getSelectedItem();

        if(batchStage.equals("Stage 01")) {
            min = 20.0;
            max = 30.0;
        } else if(batchStage.equals("Stage 02")) {
            min = 30.0;
            max = 40.0;
        } else if(batchStage.equals("Stage 03")) {
            min = 40.0;
            max = 50.0;
        }
    }
}

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
    public JFXComboBox<String> searchRoom;
    public JFXButton btnCheck;
    public Label lblTemperatureNow;
    public Label lblTemperatureStatus;

    public void searchRoomOnAction(ActionEvent actionEvent) {

    }

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
    }
}

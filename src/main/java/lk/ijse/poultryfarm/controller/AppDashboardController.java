package lk.ijse.poultryfarm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppDashboardController implements Initializable {
    
    public AnchorPane ancAppWindow;

    public void openEmployeeWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EmployeeDashboard.fxml");
    }

    public void openFoodWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/FoodDashboard.fxml");
    }

    public void goOwnerAccountOnAction(ActionEvent actionEvent) {
        navigateTo("/view/OwnerAccount.fxml");
    }

    public void openBillWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/BillManagementPage.fxml");
    }

    public void openBatchWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/BatchDashboard.fxml");
    }

    public void openWasteWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/WasteManagementPage.fxml");
    }

    public void openReportWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/BatchDashboard.fxml");
    }

    public void navigateTo(String path) {
        try {
            ancAppWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancAppWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancAppWindow.heightProperty());

            ancAppWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }
}

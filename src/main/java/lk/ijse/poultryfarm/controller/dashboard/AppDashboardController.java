package lk.ijse.poultryfarm.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.poultryfarm.controller.ButtonScale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppDashboardController implements Initializable {
    
    public AnchorPane ancAppWindow;
    public JFXButton btnBatch;
    public JFXButton btnFood;
    public JFXButton btnBill;
    public JFXButton btnWaste;
    public JFXButton btnReport;
    public JFXButton btnUser;
    public JFXButton btnEmployee;

    public void openEmployeeWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/dashboard/EmployeeDashboard.fxml");
    }

    public void openFoodWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/dashboard/FoodDashboard.fxml");
    }

    public void goOwnerAccountOnAction(ActionEvent actionEvent) {
        navigateTo("/view/owner/OwnerAccount.fxml");
    }

    public void openBillWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/BillManagementPage.fxml");
    }

    public void openBatchWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/dashboard/BatchDashboard.fxml");
    }

    public void openWasteWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/WasteManagementPage.fxml");
    }

    public void openReportWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/dashboard/BatchDashboard.fxml");
        ButtonScale.buttonScaling(btnBatch);
        ButtonScale.buttonScaling(btnFood);
        ButtonScale.buttonScaling(btnBill);
        ButtonScale.buttonScaling(btnWaste);
        ButtonScale.buttonScaling(btnReport);
        ButtonScale.buttonScaling(btnUser);
        ButtonScale.buttonScaling(btnEmployee);
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

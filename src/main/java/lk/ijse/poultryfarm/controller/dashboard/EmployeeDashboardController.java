package lk.ijse.poultryfarm.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeDashboardController implements Initializable {
    public AnchorPane ancEmployeeWindow;

    public void goSalaryManagementPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/employee/SalaryManagementPage.fxml");
    }

    public void goEmployeeDetailsPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/employee/EmployeeDetailsPage.fxml");
    }

    public void goEmployeeAttendancePageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/employee/EmployeeAttendancePage.fxml");
    }

    public void navigateTo(String path) {
        try {
            ancEmployeeWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancEmployeeWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancEmployeeWindow.heightProperty());

            ancEmployeeWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/employee/EmployeeDetailsPage.fxml");
    }
}

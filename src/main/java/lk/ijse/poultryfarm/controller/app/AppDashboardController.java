package lk.ijse.poultryfarm.controller.app;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppDashboardController implements Initializable {
    
    public AnchorPane ancAppWindow;
    public JFXButton btnOwnerAccount;

    public void openOtherWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/other/OtherDashboard.fxml");
    }

    public void openEmployeeWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/employee/EmployeeDashboard.fxml");
    }

    public void OpenBatchWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/batch/BatchDashboard.fxml");
    }

    public void openFoodWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/food/FoodDashboard.fxml");
    }

    public void openOrderWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/order/OrderDashboard.fxml");
    }

    public void goOwnerAccountOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/owner/OwnerAccount.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/app/batch/BatchDashboard.fxml");
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

    public void changeUsernameOnMouseMoved(MouseEvent mouseEvent) {
        btnOwnerAccount.setText(lk.ijse.poultryfarm.controller.login.LoginPageController.txtUsername);
    }

}

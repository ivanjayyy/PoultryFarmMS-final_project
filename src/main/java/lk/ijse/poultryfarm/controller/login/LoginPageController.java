package lk.ijse.poultryfarm.controller.login;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    public JFXButton btnLogin;

    public void goAppWindowOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/app/AppDashboard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}

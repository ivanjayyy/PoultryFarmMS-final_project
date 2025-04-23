package lk.ijse.poultryfarm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    public JFXButton btnLogin;
    public PasswordField inputPassword;
    public TextField inputUsername;

    public void goAppWindowOnAction(ActionEvent actionEvent) throws IOException {
        String username1 = "Ivan";
        String username2 = "a";
        String password1 = "2003";
        String password2 = "a";

        String txtUsername = inputUsername.getText();
        String txtPassword = inputPassword.getText();

        if(txtUsername.equals(username1) && txtPassword.equals(password1) || txtUsername.equals(username2) && txtPassword.equals(password2)) {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/AppDashboard.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username or Password");
            alert.showAndWait();
        }
    }
}

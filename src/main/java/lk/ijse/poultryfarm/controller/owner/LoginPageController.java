package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.model.OwnerModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    public JFXButton btnLogin;
    public PasswordField inputPassword;
    public TextField inputUsername;

    OwnerModel ownerModel = new OwnerModel();

    public void goAppWindowOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String txtUsername = inputUsername.getText();
        String txtPassword = inputPassword.getText();

        String username = ownerModel.ownerUsername();
        String password = ownerModel.ownerPassword();

        if(txtUsername.equals(username) && txtPassword.equals(password)) {
            Stage currentstage = (Stage) btnLogin.getScene().getWindow();
            currentstage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard/AppDashboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username or Password");
            alert.showAndWait();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnLogin);
        ButtonScale.textFieldScaling(inputUsername);
        ButtonScale.textFieldScaling(inputPassword);
    }
}

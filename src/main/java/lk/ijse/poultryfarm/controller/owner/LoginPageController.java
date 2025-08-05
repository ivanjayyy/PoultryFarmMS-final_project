package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dao.custom.impl.OwnerDAOImpl;
import lk.ijse.poultryfarm.util.EnterKeyAction;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    public JFXButton btnLogin;
    public PasswordField inputPassword;
    public TextField inputUsername;
    public JFXButton btnForgotPassword;

    OwnerDAOImpl ownerModel = new OwnerDAOImpl();

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

        } else if(!txtUsername.equals(username)) {
            new Alert(Alert.AlertType.ERROR,"Invalid Username").show();
        } else if(!txtPassword.equals(password)) {
            new Alert(Alert.AlertType.ERROR,"Invalid Password").show();
        } else {
            new Alert(Alert.AlertType.ERROR,"Invalid Username and Password").show();
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
        ButtonScale.buttonScaling(btnForgotPassword);

        EnterKeyAction.setEnterKeyMove(inputUsername, inputPassword);
    }

    public void goForgotPasswordWindowOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Change the Password ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try{
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Error in opening Forgot Password window").show();
            }
        }

    }
}

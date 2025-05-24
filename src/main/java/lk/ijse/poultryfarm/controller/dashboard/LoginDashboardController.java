package lk.ijse.poultryfarm.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.model.OwnerModel;

public class LoginDashboardController implements Initializable {

    public AnchorPane ancLoginWindow;
    public JFXButton btnLogin;
    public JFXButton btnCreate;

    public void goCreateAccountPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/owner/CreateAccountPage.fxml");
    }

    public void goLoginPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/owner/LoginPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/owner/LoginPage.fxml");

        ButtonScale.buttonScaling(btnLogin);
        ButtonScale.buttonScaling(btnCreate);

        OwnerModel ownerModel = new OwnerModel();
        try {
            if(ownerModel.hasOwner()) {
                btnCreate.setDisable(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error");
        }

    }

    public void navigateTo(String path) {
        try {
            ancLoginWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancLoginWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancLoginWindow.heightProperty());

            ancLoginWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

}

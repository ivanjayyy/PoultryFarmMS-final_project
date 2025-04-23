package lk.ijse.poultryfarm.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginDashboardController implements Initializable {

    public AnchorPane ancLoginWindow;

    public void goCreateAccountPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/CreateAccountPage.fxml");
    }

    public void goLoginPageOnAction(ActionEvent actionEvent) {

        navigateTo("/view/LoginPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/LoginWindow.fxml");
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

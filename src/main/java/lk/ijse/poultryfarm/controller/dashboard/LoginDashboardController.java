package lk.ijse.poultryfarm.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dao.custom.impl.OwnerDAOImpl;

public class LoginDashboardController implements Initializable {

    public AnchorPane ancLoginWindow;
    public JFXButton btnLogin;
    public JFXButton btnCreate;
    public JFXButton btnEdit;
    public ImageView profileImageView;

    public void goCreateAccountPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/owner/CreateAccountPage.fxml");
    }

    public void goLoginPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/owner/LoginPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnLogin);
        ButtonScale.buttonScaling(btnCreate);
        ButtonScale.buttonScaling(btnEdit);

        OwnerDAOImpl ownerModel = new OwnerDAOImpl();
        try {
            if(ownerModel.hasOwner()) {
                btnCreate.setDisable(true);
                navigateTo("/view/owner/LoginPage.fxml");
            } else {
                navigateTo("/view/owner/CreateAccountPage.fxml");
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

    public void editLogoOnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(getStage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
        }
    }

    private Stage getStage() {
        return (Stage) profileImageView.getScene().getWindow();
    }
}

package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.mail.ForgotPasswordController;
import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.model.OwnerModel;
import lk.ijse.poultryfarm.util.EnterKeyAction;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountPageController implements Initializable {
    public TextField inputFullName;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputEmail;

    private final OwnerModel ownerModel = new OwnerModel();
    public JFXButton btnCreate;

    private final String patternUsername = "^[a-zA-Z0-9_-]+$";
    private final String patternEmail = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,}$";
    private final String patternFullName = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";

    private final String pattern1WeakPassword = "^[A-Za-z]+$";
    private final String pattern2WeakPassword = "^[0-9]+$";
    private final String pattern3WeakPassword = "^[A-Za-z0-9]+$";
    private final String patternNormalPassword = "^[A-Za-z0-9]{6,}$";

    public void createAccountOnAction(ActionEvent actionEvent) {
        String ownerId = "O001";
        String fullName = inputFullName.getText();
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String email = inputEmail.getText();

        OwnerDto ownerDto = new OwnerDto(ownerId,fullName,username,password,email);

        if(fullName.equals("") || username.equals("") || password.equals("") || email.equals("")) {
            new Alert(Alert.AlertType.ERROR,"There are Empty Fields. Please Fill Them.").show();

        } else {
            try {
                boolean isSaved = ownerModel.saveOwner(ownerDto);
                resetPage();

                if(isSaved) {
                    new Alert(Alert.AlertType.INFORMATION,"Account Created Successfully").show();

                    String message = "Hello "+username+",\nWelcome to J.M.R. Farm House Management System.\nYou created a New Account successfully";
                    ForgotPasswordController.sendMail("New Account Created Successfully.", message);

                } else {
                    new Alert(Alert.AlertType.ERROR,"Account Creation Failed").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Account Creation Failed").show();
                e.printStackTrace();
            }
        }
    }

    private void resetPage() {
        inputFullName.clear();
        inputUsername.clear();
        inputPassword.clear();
        inputEmail.clear();
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnCreate);
        ButtonScale.textFieldScaling(inputFullName);
        ButtonScale.textFieldScaling(inputUsername);
        ButtonScale.textFieldScaling(inputPassword);
        ButtonScale.textFieldScaling(inputEmail);

        EnterKeyAction.setEnterKeyMove(inputFullName, inputUsername);
        EnterKeyAction.setEnterKeyMove(inputUsername, inputPassword);
        EnterKeyAction.setEnterKeyMove(inputPassword, inputEmail);

        inputUsername.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternUsername) || newVal.isEmpty()) {
                inputUsername.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");

            } else {
                inputUsername.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
            }
        });

        inputEmail.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternEmail) || newVal.isEmpty()) {
                inputEmail.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");

            } else {
                inputEmail.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
            }
        });

        inputFullName.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternFullName) || newVal.isEmpty()) {
                inputFullName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");

            } else {
                inputFullName.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
            }
        });

        inputPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternNormalPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: orange; -fx-border-width: 3");

            } else if (newVal.matches(pattern1WeakPassword) || newVal.matches(pattern2WeakPassword) || newVal.matches(pattern3WeakPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: red; -fx-border-width: 3");

            } else if (newVal.isEmpty()){
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");

            } else {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: green; -fx-border-width: 3");
            }
        });
    }
}

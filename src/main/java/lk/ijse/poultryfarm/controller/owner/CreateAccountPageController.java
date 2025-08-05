package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.mail.ForgotPasswordController;
import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.dao.custom.impl.OwnerDAOImpl;
import lk.ijse.poultryfarm.util.EnterKeyAction;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreateAccountPageController implements Initializable {
    public TextField inputFullName;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputEmail;

    private final OwnerDAOImpl ownerModel = new OwnerDAOImpl();
    public JFXButton btnCreate;

    private final String patternUsername = "^[a-zA-Z0-9_-]+$";
    private final String patternEmail = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,}$";
    private final String patternFullName = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";

    private final String pattern1WeakPassword = "^[A-Za-z]+$";
    private final String pattern2WeakPassword = "^[0-9]+$";
    private final String pattern3WeakPassword = "^[A-Za-z0-9]+$";
    private final String patternNormalPassword = "^[A-Za-z0-9]{6,}$";
    public Label lblPasswordDifficulty;

    public void createAccountOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (ownerModel.hasOwner()) {
            new Alert(Alert.AlertType.ERROR, "Already have a User Account. Please Login.").show();
            return;
        }

        String ownerId = "O001";
        String fullName = inputFullName.getText();
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String email = inputEmail.getText();

        OwnerDto ownerDto = new OwnerDto(ownerId,fullName,username,password,email);

        if(fullName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "There are Empty Fields. Please Fill Them.").show();

        } else if(!fullName.matches(patternFullName) || !username.matches(patternUsername) || !email.matches(patternEmail)) {
            new Alert(Alert.AlertType.ERROR,"Invalid Inputs. Please Try Again.").show();

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
        btnCreate.setDisable(true);

        ButtonScale.buttonScaling(btnCreate);
        ButtonScale.textFieldScaling(inputFullName);
        ButtonScale.textFieldScaling(inputUsername);
        ButtonScale.textFieldScaling(inputPassword);
        ButtonScale.textFieldScaling(inputEmail);

        EnterKeyAction.setEnterKeyMove(inputFullName, inputUsername);
        EnterKeyAction.setEnterKeyMove(inputUsername, inputPassword);
        EnterKeyAction.setEnterKeyMove(inputPassword, inputEmail);

        AtomicBoolean isValidName = new AtomicBoolean(false);
        AtomicBoolean isValidUsername = new AtomicBoolean(false);
        AtomicBoolean isValidPassword = new AtomicBoolean(false);
        AtomicBoolean isValidEmail = new AtomicBoolean(false);

        inputUsername.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternUsername)) {
                inputUsername.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidUsername.set(true);

                if (isValidPassword.get() && isValidEmail.get() && isValidName.get()){
                    btnCreate.setDisable(false);
                }

            } else if (newVal.isEmpty()) {
                inputUsername.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidUsername.set(false);
                btnCreate.setDisable(true);

            } else {
                inputUsername.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidUsername.set(false);
                btnCreate.setDisable(true);
            }
        });

        inputEmail.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternEmail)) {
                inputEmail.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidEmail.set(true);

                if(isValidPassword.get() && isValidName.get() && isValidUsername.get()){
                    btnCreate.setDisable(false);
                }

            } else if (newVal.isEmpty()) {
                inputEmail.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidEmail.set(false);
                btnCreate.setDisable(true);

            } else {
                inputEmail.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidEmail.set(false);
                btnCreate.setDisable(true);
            }
        });

        inputFullName.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternFullName)) {
                inputFullName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidName.set(true);

                if(isValidPassword.get() && isValidEmail.get() && isValidUsername.get()){
                    btnCreate.setDisable(false);
                }

            } else if (newVal.isEmpty()) {
                inputFullName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidName.set(false);
                btnCreate.setDisable(true);

            } else {
                inputFullName.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-radius: 30; -fx-border-color: gray;");
                isValidName.set(false);
                btnCreate.setDisable(true);
            }
        });

        inputPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternNormalPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: orange; -fx-border-width: 3");

                lblPasswordDifficulty.setText("Normal");
                lblPasswordDifficulty.setStyle("-fx-text-fill: orange");

                isValidPassword.set(true);

                if(isValidEmail.get() && isValidName.get() && isValidUsername.get()){
                    btnCreate.setDisable(false);
                }

            } else if (newVal.matches(pattern1WeakPassword) || newVal.matches(pattern2WeakPassword) || newVal.matches(pattern3WeakPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: red; -fx-border-width: 3");

                lblPasswordDifficulty.setText("Weak");
                lblPasswordDifficulty.setStyle("-fx-text-fill: red");

                isValidPassword.set(true);

                if(isValidEmail.get() && isValidName.get() && isValidUsername.get()){
                    btnCreate.setDisable(false);
                }

            } else if (newVal.isEmpty()){
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: gray;");

                lblPasswordDifficulty.setText("");
                lblPasswordDifficulty.setStyle("-fx-text-fill: gray");

                isValidPassword.set(false);
                btnCreate.setDisable(true);

            } else {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: green; -fx-border-width: 3");

                lblPasswordDifficulty.setText("Strong");
                lblPasswordDifficulty.setStyle("-fx-text-fill: green");

                isValidPassword.set(true);

                if(isValidEmail.get() && isValidName.get() && isValidUsername.get()){
                    btnCreate.setDisable(false);
                }
            }
        });
    }
}

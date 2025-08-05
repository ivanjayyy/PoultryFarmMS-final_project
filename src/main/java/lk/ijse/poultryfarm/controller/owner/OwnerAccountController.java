package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.dao.custom.impl.OwnerDAOImpl;
import lk.ijse.poultryfarm.util.EnterKeyAction;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class OwnerAccountController implements Initializable {
    public TextField inputFullName;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputEmail;
    public JFXButton btnUpdateOwner;
    public Label lblPasswordDifficulty;

    private final String patternUsername = "^[a-zA-Z0-9_-]+$";
    private final String patternEmail = "^[a-zA-Z0-9._%+-]{1,64}@[a-zA-Z0-9.-]{1,255}\\.[a-zA-Z]{2,}$";
    private final String patternFullName = "^[A-Z][a-z]+(?: [A-Z][a-z]+)*$";

    private final String pattern1WeakPassword = "^[A-Za-z]+$";
    private final String pattern2WeakPassword = "^[0-9]+$";
    private final String pattern3WeakPassword = "^[A-Za-z0-9]+$";
    private final String patternNormalPassword = "^[A-Za-z0-9]{6,}$";

    OwnerDAOImpl ownerModel = new OwnerDAOImpl();

    public void updateOwnerOnAction(ActionEvent actionEvent) {
        if (btnUpdateOwner.getText().equals("UPDATE")) {
            String ownerId = "O001";
            String fullName = inputFullName.getText();
            String username = inputUsername.getText();
            String password = inputPassword.getText();
            String email = inputEmail.getText();

            OwnerDto ownerDto = new OwnerDto(ownerId,fullName,username,password,email);

            try {
                boolean isUpdated = ownerModel.updateOwner(ownerDto);

                if(isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION,"Account Updated Successfully").show();
                    initialize(null,null);

                } else {
                    new Alert(Alert.AlertType.ERROR,"Account Update Failed").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Account Update Failed").show();
                e.printStackTrace();
            }

        } else if (btnUpdateOwner.getText().equals("EDIT DETAILS")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to edit account details?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                inputFullName.setEditable(true);
                inputUsername.setEditable(true);
                inputPassword.setEditable(true);
                inputEmail.setEditable(true);
                btnUpdateOwner.setText("UPDATE");

                btnUpdateOwner.setDisable(true);

                inputFullName.setText("");
                inputUsername.setText("");
                inputPassword.setText("");
                inputEmail.setText("");
            }

        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            OwnerDto ownerDto = ownerModel.getOwner();

            if(ownerDto != null) {
                inputFullName.setText(ownerDto.getName());
                inputUsername.setText(ownerDto.getUsername());
                inputPassword.setText(ownerDto.getPassword());
                inputEmail.setText(ownerDto.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving owner details").show();
        }

        inputFullName.setEditable(false);
        inputUsername.setEditable(false);
        inputPassword.setEditable(false);
        inputEmail.setEditable(false);
        btnUpdateOwner.setText("EDIT DETAILS");
        ButtonScale.buttonScaling(btnUpdateOwner);

        EnterKeyAction.setEnterKeyMove(inputFullName, inputUsername);
        EnterKeyAction.setEnterKeyMove(inputUsername, inputPassword);
        EnterKeyAction.setEnterKeyMove(inputPassword, inputEmail);

        AtomicBoolean isValidName = new AtomicBoolean(false);
        AtomicBoolean isValidUsername = new AtomicBoolean(false);
        AtomicBoolean isValidPassword = new AtomicBoolean(false);
        AtomicBoolean isValidEmail = new AtomicBoolean(false);

        inputUsername.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternUsername)) {
                inputUsername.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidUsername.set(true);

                if (isValidPassword.get() && isValidEmail.get() && isValidName.get()){
                    btnUpdateOwner.setDisable(false);
                }

            } else if (newVal.isEmpty()) {
                inputUsername.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidUsername.set(false);
                btnUpdateOwner.setDisable(true);

            } else {
                inputUsername.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidUsername.set(false);
                btnUpdateOwner.setDisable(true);
            }
        });

        inputEmail.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternEmail)) {
                inputEmail.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidEmail.set(true);

                if(isValidPassword.get() && isValidName.get() && isValidUsername.get()){
                    btnUpdateOwner.setDisable(false);
                }

            } else if (newVal.isEmpty()) {
                inputEmail.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidEmail.set(false);
                btnUpdateOwner.setDisable(true);

            } else {
                inputEmail.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidEmail.set(false);
                btnUpdateOwner.setDisable(true);
            }
        });

        inputFullName.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternFullName)) {
                inputFullName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidName.set(true);

                if(isValidPassword.get() && isValidEmail.get() && isValidUsername.get()){
                    btnUpdateOwner.setDisable(false);
                }

            } else if (newVal.isEmpty()) {
                inputFullName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidName.set(false);
                btnUpdateOwner.setDisable(true);

            } else {
                inputFullName.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                isValidName.set(false);
                btnUpdateOwner.setDisable(true);
            }
        });

        inputPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternNormalPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 3px 0; -fx-border-color: orange;");

                lblPasswordDifficulty.setText("Normal");
                lblPasswordDifficulty.setStyle("-fx-text-fill: orange");

                isValidPassword.set(true);

                if(isValidEmail.get() && isValidName.get() && isValidUsername.get()){
                       btnUpdateOwner.setDisable(false);
                }

            } else if (newVal.matches(pattern1WeakPassword) || newVal.matches(pattern2WeakPassword) || newVal.matches(pattern3WeakPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-color: red; -fx-border-width: 0 0 3px 0;");

                lblPasswordDifficulty.setText("Weak");
                lblPasswordDifficulty.setStyle("-fx-text-fill: red");

                isValidPassword.set(true);

                if(isValidEmail.get() && isValidName.get() && isValidUsername.get()){
                    btnUpdateOwner.setDisable(false);
                }

            } else if (newVal.isEmpty()){
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");

                lblPasswordDifficulty.setText("");
                lblPasswordDifficulty.setStyle("-fx-text-fill: gray");

                isValidPassword.set(false);
                btnUpdateOwner.setDisable(true);

            } else {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-color: green; -fx-border-width: 0 0 3px 0;");

                lblPasswordDifficulty.setText("Strong");
                lblPasswordDifficulty.setStyle("-fx-text-fill: green");

                isValidPassword.set(true);

                if(isValidEmail.get() && isValidName.get() && isValidUsername.get()){
                    btnUpdateOwner.setDisable(false);
                }
            }
        });
    }
}

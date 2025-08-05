package lk.ijse.poultryfarm.controller.mail;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dao.custom.impl.OwnerDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChangePasswordController implements Initializable {
    public TextField inputPassword;
    public TextField confirmPassword;
    public Label lblPasswordDifficulty;
    public JFXButton btnSave;

    private final String pattern1WeakPassword = "^[A-Za-z]+$";
    private final String pattern2WeakPassword = "^[0-9]+$";
    private final String pattern3WeakPassword = "^[A-Za-z0-9]+$";
    private final String patternNormalPassword = "^[A-Za-z0-9]{6,}$";

    public void savePasswordOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(inputPassword.getText().equals(confirmPassword.getText())){
            OwnerDAOImpl ownerModel = new OwnerDAOImpl();
            boolean isSuccess = ownerModel.changePassword(inputPassword.getText());

            if(isSuccess) {
                new Alert(Alert.AlertType.INFORMATION, "Password Updated Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Password Update Failed").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Password Does Not Match").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnSave);
        btnSave.setDisable(true);

        AtomicBoolean isValidPassword = new AtomicBoolean(false);
        AtomicBoolean isConfirmed = new AtomicBoolean(false);

        inputPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(patternNormalPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: orange; -fx-border-width: 3");

                lblPasswordDifficulty.setText("Normal");
                lblPasswordDifficulty.setStyle("-fx-text-fill: orange");
                isValidPassword.set(true);

                if(isConfirmed.get()) {
                    btnSave.setDisable(false);
                }

            } else if (newVal.matches(pattern1WeakPassword) || newVal.matches(pattern2WeakPassword) || newVal.matches(pattern3WeakPassword)) {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: red; -fx-border-width: 3");

                lblPasswordDifficulty.setText("Weak");
                lblPasswordDifficulty.setStyle("-fx-text-fill: red");
                isValidPassword.set(true);

                if(isConfirmed.get()) {
                    btnSave.setDisable(false);
                }

            } else if (newVal.isEmpty()){
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: gray;");

                lblPasswordDifficulty.setText("");
                lblPasswordDifficulty.setStyle("-fx-text-fill: gray");
                isValidPassword.set(false);
                btnSave.setDisable(true);

            } else {
                inputPassword.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-radius: 20; -fx-border-color: green; -fx-border-width: 3");

                lblPasswordDifficulty.setText("Strong");
                lblPasswordDifficulty.setStyle("-fx-text-fill: green");
                isValidPassword.set(true);

                if(isConfirmed.get()) {
                    btnSave.setDisable(false);
                }
            }
        });

        confirmPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal.matches(inputPassword.getText())) {
                isConfirmed.set(true);
                if(isValidPassword.get()){
                    btnSave.setDisable(false);
                }
            } else {
                isConfirmed.set(false);
                btnSave.setDisable(true);
            }
        });
    }
}

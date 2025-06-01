package lk.ijse.poultryfarm.controller.mail;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.model.OwnerModel;

import java.sql.SQLException;

public class ChangePasswordController {
    public TextField inputPassword;
    public TextField confirmPassword;
    public Label lblPasswordDifficulty;
    public JFXButton btnSave;

    private final String pattern1WeakPassword = "^[A-Za-z]+$";
    private final String pattern2WeakPassword = "^[0-9]+$";
    private final String patternNormalPassword = "^[A-Za-z0-9]+$";

    public void savePasswordOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(inputPassword.getText().equals(confirmPassword.getText())){
            OwnerModel ownerModel = new OwnerModel();
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

    public void checkPasswordDifficultyOnMouseEntered(MouseEvent mouseEvent) {
        if (inputPassword.getText().matches(pattern1WeakPassword) || inputPassword.getText().matches(pattern2WeakPassword)) {
            lblPasswordDifficulty.setText("Weak Password");
            lblPasswordDifficulty.setStyle("-fx-text-fill: red");
        } else if (inputPassword.getText().isEmpty()) {
            lblPasswordDifficulty.setText("Enter Password");
            lblPasswordDifficulty.setStyle("-fx-text-fill: gray");
        } else if(inputPassword.getText().matches(patternNormalPassword)) {
            lblPasswordDifficulty.setText("Normal Password");
            lblPasswordDifficulty.setStyle("-fx-text-fill: orange");
        } else {
            lblPasswordDifficulty.setText("Strong Password");
            lblPasswordDifficulty.setStyle("-fx-text-fill: green");
        }
    }
}

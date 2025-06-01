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

    private final String patternStrongPassword = "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$\n";
    private final String patternWeakPassword = "^[A-Za-z0-9]{6,}$\n";

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
        if(inputPassword.getText().matches(patternStrongPassword)){
            lblPasswordDifficulty.setText("Strong Password");
            lblPasswordDifficulty.setStyle("-fx-text-fill: green");
        } else if (inputPassword.getText().matches(patternWeakPassword)) {
            lblPasswordDifficulty.setText("Weak Password");
            lblPasswordDifficulty.setStyle("-fx-text-fill: red");
        } else if (inputPassword.getText().isEmpty()) {
            lblPasswordDifficulty.setText("Enter Password");
        }
    }
}

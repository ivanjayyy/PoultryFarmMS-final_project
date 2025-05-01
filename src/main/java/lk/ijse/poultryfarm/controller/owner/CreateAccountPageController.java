package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.model.OwnerModel;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAccountPageController implements Initializable {
    public TextField inputFullName;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputEmail;

    private final OwnerModel ownerModel = new OwnerModel();
    public JFXButton btnCreate;

    public void createAccountOnAction(ActionEvent actionEvent) {
        String ownerId = "O001";
        String fullName = inputFullName.getText();
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        String email = inputEmail.getText();

        OwnerDto ownerDto = new OwnerDto(ownerId,fullName,username,password,email);

        try {
            boolean isSaved = ownerModel.saveOwner(ownerDto);
            resetPage();

            if(isSaved) {
                new Alert(Alert.AlertType.INFORMATION,"Account Created Successfully").show();

            } else {
                new Alert(Alert.AlertType.ERROR,"Account Creation Failed").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Account Creation Failed").show();
            e.printStackTrace();
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
    }
}

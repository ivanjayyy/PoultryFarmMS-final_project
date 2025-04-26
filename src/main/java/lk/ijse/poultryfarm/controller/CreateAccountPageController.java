package lk.ijse.poultryfarm.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.model.OwnerModel;

public class CreateAccountPageController {
    public TextField inputFullName;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputEmail;

    private final OwnerModel ownerModel = new OwnerModel();

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
}

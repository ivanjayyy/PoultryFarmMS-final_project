package lk.ijse.poultryfarm.controller.owner;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.model.OwnerModel;
import lk.ijse.poultryfarm.util.EnterKeyAction;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class OwnerAccountController implements Initializable {
    public TextField inputFullName;
    public TextField inputUsername;
    public TextField inputPassword;
    public TextField inputEmail;
    public JFXButton btnUpdateOwner;

    OwnerModel ownerModel = new OwnerModel();

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
    }
}

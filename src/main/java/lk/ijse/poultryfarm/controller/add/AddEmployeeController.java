package lk.ijse.poultryfarm.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.employee.EmployeeDetailsPageController;
import lk.ijse.poultryfarm.dto.EmployeeDto;
import lk.ijse.poultryfarm.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddEmployeeController implements Initializable {
    public Label lblEmployeeId;
    public TextField inputName;
    public JFXComboBox<String> inputEmployeeType;
    public TextField inputContact;
    public TextField inputDailyWage;
    public JFXButton btnSave;

    private final String patternName = "^[A-Za-z ]+$";
    private final String patternContact = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    private final String patternDailyWage = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final EmployeeModel employeeModel = new EmployeeModel();

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String employeeId = lblEmployeeId.getText();
        String name = inputName.getText();
        String fullTime = inputEmployeeType.getValue();
        String contact = inputContact.getText();
        String dailyWage = inputDailyWage.getText();

        EmployeeDto employeeDto = new EmployeeDto(employeeId,name,fullTime,contact,Double.parseDouble(dailyWage));

        if(EmployeeDetailsPageController.updateEmployee){
            if(employeeModel.checkContactDuplicate(contact) > 1) {
                new Alert(Alert.AlertType.ERROR,"Contact number already exists").show();
                return;
            }

            boolean isUpdated = employeeModel.updateEmployee(employeeDto);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Employee has been updated successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

                btnSave.setText("SAVE");

            }else {
                new Alert(Alert.AlertType.ERROR,"Employee could not be updated").show();
            }

        }else {
            if(employeeModel.checkContactDuplicate(contact) > 0) {
                new Alert(Alert.AlertType.ERROR,"Contact number already exists").show();
                return;
            }

            boolean isSaved = employeeModel.saveEmployee(employeeDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Save Failed").show();
            }
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reset();

        AtomicBoolean validName = new AtomicBoolean(false);
        AtomicBoolean validContact = new AtomicBoolean(false);
        AtomicBoolean validDailyWage = new AtomicBoolean(false);

        btnSave.setDisable(true);

        try {
            inputName.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternName)) {
                    inputName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validName.set(true);

                    if(validContact.get() && validDailyWage.get()){
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputName.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validName.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputName.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validName.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputContact.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternContact)) {
                    inputContact.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validContact.set(true);

                    if(validName.get() && validDailyWage.get()){
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputContact.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validContact.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputContact.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validContact.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputDailyWage.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternDailyWage)) {
                    inputDailyWage.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validDailyWage.set(true);

                    if(validName.get() && validContact.get()) {
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputDailyWage.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validDailyWage.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputDailyWage.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validDailyWage.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputEmployeeType.getItems().addAll("Full Time","Temporary");
            inputEmployeeType.setValue("Temporary");
            loadNextId();
            ButtonScale.buttonScaling(btnSave);

            if(EmployeeDetailsPageController.updateEmployee){
                lblEmployeeId.setText(EmployeeDetailsPageController.selectedEmployeeId);
                inputName.setText(EmployeeDetailsPageController.selectedEmployeeName);
                inputEmployeeType.setValue(EmployeeDetailsPageController.selectedEmployeeFullTime);
                inputContact.setText(EmployeeDetailsPageController.selectedEmployeeContact);
                inputDailyWage.setText(EmployeeDetailsPageController.selectedEmployeeDailyWage);

                btnSave.setText("UPDATE");
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Error in retrieving employee id").show();
            e.printStackTrace();
        }
    }

    private void reset() {
        lblEmployeeId.setText("");
        inputName.setText("");
        inputEmployeeType.setValue("");
        inputContact.setText("");
        inputDailyWage.setText("");
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextId);
    }
}

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
import lk.ijse.poultryfarm.dto.EmployeeDto;
import lk.ijse.poultryfarm.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    public Label lblEmployeeId;
    public TextField inputName;
    public JFXComboBox<Boolean> inputEmployeeType;
    public TextField inputContact;
    public TextField inputDailyWage;
    public JFXButton btnSave;

    private final EmployeeModel employeeModel = new EmployeeModel();

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String employeeId = lblEmployeeId.getText();
        String name = inputName.getText();
        String fullTime = inputEmployeeType.getValue().toString();
        String contact = inputContact.getText();
        String dailyWage = inputDailyWage.getText();

        EmployeeDto employeeDto = new EmployeeDto(employeeId,name,Boolean.parseBoolean(fullTime),contact,Double.parseDouble(dailyWage));

        boolean isSaved = employeeModel.saveEmployee(employeeDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION,"Employee Saved Successfully").show();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR,"Employee Save Failed").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputEmployeeType.getItems().addAll(true, false);
            loadNextId();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Error in retrieving employee id").show();
            e.printStackTrace();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextId);
    }
}

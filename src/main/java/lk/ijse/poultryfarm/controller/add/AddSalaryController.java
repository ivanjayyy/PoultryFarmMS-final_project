package lk.ijse.poultryfarm.controller.add;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.employee.EmployeeDetailsPageController;
import lk.ijse.poultryfarm.dto.SalaryDto;
import lk.ijse.poultryfarm.model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSalaryController implements Initializable {
    public Label lblSalaryId;
    public Label lblEmployeeId;
    public TextField inputAmount;
    public DatePicker inputDate;
    public JFXButton btnSave;

    private final SalaryModel salaryModel = new SalaryModel();

    public void saveSalaryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId = lblSalaryId.getText();
        String employeeId = lblEmployeeId.getText();
        String amount = inputAmount.getText();
        String date = inputDate.getValue().toString();

        SalaryDto salaryDto = new SalaryDto(salaryId,employeeId,Double.parseDouble(amount),date);
        boolean isSaved = salaryModel.saveSalary(salaryDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION,"Salary Saved").show();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR,"Salary Failed").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();
            ButtonScale.buttonScaling(btnSave);
            lblEmployeeId.setText(EmployeeDetailsPageController.selectedEmployeeId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving customer id").show();
        }
    }

    private void loadNextId() {
        try {
            String nextId = salaryModel.getNextSalaryId();
            lblSalaryId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

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
import lk.ijse.poultryfarm.dao.custom.impl.ChickBatchDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.DailyAttendanceDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.SalaryDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSalaryController implements Initializable {
    public Label lblSalaryId;
    public Label lblEmployeeId;
    public TextField inputAmount;
    public DatePicker inputDate;
    public JFXButton btnSave;

    private final String patternAmount = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final SalaryDAOImpl salaryModel = new SalaryDAOImpl();
    private final DailyAttendanceDAOImpl dailyAttendanceModel = new DailyAttendanceDAOImpl();
    private final ChickBatchDAOImpl chickBatchModel = new ChickBatchDAOImpl();
    private final EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();

    public void saveSalaryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId = lblSalaryId.getText();
        String employeeId = lblEmployeeId.getText();
        String amount = inputAmount.getText();
        String date = inputDate.getValue().toString();

        SalaryDto salaryDto = new SalaryDto(salaryId,employeeId,Double.parseDouble(amount),date);
        boolean isSaved = salaryModel.saveSalary(salaryDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Salary Saved").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Salary Saving Failed").show();
            }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSave.setDisable(true);

        try {
            inputAmount.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternAmount)) {
                    inputAmount.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(false);

                } else if (newVal.isEmpty()) {
                    inputAmount.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(true);

                } else {
                    inputAmount.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(true);
                }
            });

            btnSave.setText("SAVE");
            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();

            ButtonScale.buttonScaling(btnSave);

            String employeeId = EmployeeDetailsPageController.selectedEmployeeId;
            lblEmployeeId.setText(employeeId);

            String currentBatchId = chickBatchModel.getCurrentBatchId();
            int attendanceCount = dailyAttendanceModel.countAttendance(employeeId,currentBatchId);

            double dailyWage = employeeModel.getDailyWage(employeeId);
            double salary = dailyWage * attendanceCount;
            inputAmount.setText(String.valueOf(salary));

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

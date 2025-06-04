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
import lk.ijse.poultryfarm.controller.employee.SalaryManagementPageController;
import lk.ijse.poultryfarm.dto.SalaryDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.DailyAttendanceModel;
import lk.ijse.poultryfarm.model.EmployeeModel;
import lk.ijse.poultryfarm.model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddSalaryController implements Initializable {
    public Label lblSalaryId;
    public Label lblEmployeeId;
    public TextField inputAmount;
    public DatePicker inputDate;
    public JFXButton btnSave;

    private final String patternAmount = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final SalaryModel salaryModel = new SalaryModel();
    private final DailyAttendanceModel dailyAttendanceModel = new DailyAttendanceModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();
    private final EmployeeModel employeeModel = new EmployeeModel();

    public void saveSalaryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String salaryId = lblSalaryId.getText();
        String employeeId = lblEmployeeId.getText();
        String amount = inputAmount.getText();
        String date = inputDate.getValue().toString();

//        inputAmount.setStyle("-fx-text-inner-color: black");
//        boolean isValidAmount = amount.matches(patternAmount);
//
//        if(!isValidAmount){
//            inputAmount.setStyle("-fx-text-inner-color: red");
//        }
//        if(!isValidAmount){
//            new Alert(Alert.AlertType.ERROR,"Invalid Input.").show();
//            return;
//        }

        SalaryDto salaryDto = new SalaryDto(salaryId,employeeId,Double.parseDouble(amount),date);
        if(SalaryManagementPageController.updateSalary){
            boolean isUpdated = salaryModel.updateSalary(salaryDto);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Salary Updated").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR,"Salary Update Failed").show();
            }
        } else {
            boolean isSaved = salaryModel.saveSalary(salaryDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Salary Saved").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Salary Failed").show();
            }
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputAmount.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternAmount) || newVal.isEmpty()) {
                    inputAmount.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(false);

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

            if(SalaryManagementPageController.updateSalary){
                lblSalaryId.setText(SalaryManagementPageController.selectedSalaryId);
                lblEmployeeId.setText(SalaryManagementPageController.selectedEmployeeId);
                inputAmount.setText(String.valueOf(SalaryManagementPageController.selectedSalary));
                inputDate.setValue(LocalDate.parse(SalaryManagementPageController.selectedDate));

                btnSave.setText("UPDATE");
            }
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

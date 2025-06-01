package lk.ijse.poultryfarm.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.employee.EmployeeDetailsPageController;
import lk.ijse.poultryfarm.dto.DailyAttendanceDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.DailyAttendanceModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDailyAttendanceController implements Initializable {
    public Label lblBatchId;
    public Label lblAttendanceId;
    public DatePicker inputDate;
    public Label lblEmployeeId;
    public JFXComboBox<Boolean> inputAttendance;
    public JFXButton btnSave;
    
    private final DailyAttendanceModel dailyAttendanceModel = new DailyAttendanceModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    public void saveDailyAttendanceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String attendanceId = lblAttendanceId.getText();
        String date = inputDate.getValue().toString();
        String employeeId = lblEmployeeId.getText();
        String attendance = inputAttendance.getValue().toString();

        int todayAttendanceCount = dailyAttendanceModel.checkAttendance(date, employeeId);

        if(todayAttendanceCount == 0){
            DailyAttendanceDto dailyAttendanceDto = new DailyAttendanceDto(batchId, attendanceId, date, employeeId, Boolean.parseBoolean(attendance));
            boolean isSaved = dailyAttendanceModel.saveDailyAttendance(dailyAttendanceDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Daily Attendance Saved").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Daily Attendance Failed").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Attendance already marked").show();
        }
    }

    /**
     * @param url 
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputAttendance.getItems().addAll(true,false);
            inputAttendance.setValue(true);
            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            ButtonScale.buttonScaling(btnSave);
            lblEmployeeId.setText(EmployeeDetailsPageController.selectedEmployeeId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving customer id").show();
        }
    }

    private void loadBatchId() throws SQLException, ClassNotFoundException {
        String currentBatchId = chickBatchModel.getCurrentBatchId();

        if (currentBatchId != null) {
            lblBatchId.setText(currentBatchId);
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = dailyAttendanceModel.getNextAttendanceId();
        lblAttendanceId.setText(nextId);
    }
}

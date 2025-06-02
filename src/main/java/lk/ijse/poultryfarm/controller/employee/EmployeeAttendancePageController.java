package lk.ijse.poultryfarm.controller.employee;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.DailyAttendanceDto;
import lk.ijse.poultryfarm.dto.tm.EmployeeAttendanceTm;
import lk.ijse.poultryfarm.model.DailyAttendanceModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeAttendancePageController implements Initializable {
    public TableView<EmployeeAttendanceTm> tblEmployeeAttendance;
    public TableColumn<EmployeeAttendanceTm,String> colBatchId;
    public TableColumn<EmployeeAttendanceTm,String> colAttendanceId;
    public TableColumn<EmployeeAttendanceTm,String> colDate;
    public TableColumn<EmployeeAttendanceTm,String> colEmployeeId;
    public TableColumn<EmployeeAttendanceTm,Boolean> colAttendance;

    private final DailyAttendanceModel dailyAttendanceModel = new DailyAttendanceModel();
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnReset;
    public DatePicker searchDate;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnReset);

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving daily attendance").show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            inputSearch.clear();
            searchDate.setValue(null);
            btnSearch.setDisable(true);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving daily attendance").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DailyAttendanceDto> dailyAttendanceDtos = dailyAttendanceModel.getAllDailyAttendance();
        ObservableList<EmployeeAttendanceTm> employeeAttendanceTms = FXCollections.observableArrayList();

        for (DailyAttendanceDto dailyAttendanceDto : dailyAttendanceDtos) {
            EmployeeAttendanceTm employeeAttendanceTm = new EmployeeAttendanceTm(
                    dailyAttendanceDto.getBatchId(),
                    dailyAttendanceDto.getAttendanceId(),
                    dailyAttendanceDto.getDate(),
                    dailyAttendanceDto.getEmployeeId(),
                    dailyAttendanceDto.isAttendance()
            );
            employeeAttendanceTms.add(employeeAttendanceTm);
        }
        tblEmployeeAttendance.setItems(employeeAttendanceTms);
    }

    public void AttendanceSearchOnAction(ActionEvent actionEvent) {
        try{
            ArrayList<DailyAttendanceDto> dailyAttendanceDtos = dailyAttendanceModel.searchDailyAttendance(inputSearch.getText());
            ObservableList<EmployeeAttendanceTm> employeeAttendanceTms = FXCollections.observableArrayList();

            for (DailyAttendanceDto dailyAttendanceDto : dailyAttendanceDtos) {
                EmployeeAttendanceTm employeeAttendanceTm = new EmployeeAttendanceTm(
                        dailyAttendanceDto.getBatchId(),
                        dailyAttendanceDto.getAttendanceId(),
                        dailyAttendanceDto.getDate(),
                        dailyAttendanceDto.getEmployeeId(),
                        dailyAttendanceDto.isAttendance()
                );
                employeeAttendanceTms.add(employeeAttendanceTm);
            }
            tblEmployeeAttendance.setItems(employeeAttendanceTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving daily attendance").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchDateOnAction(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        String date = searchDate.getValue().toString();
        inputSearch.setText(date);
    }
}

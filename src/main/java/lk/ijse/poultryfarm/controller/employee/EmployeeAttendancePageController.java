package lk.ijse.poultryfarm.controller.employee;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.DailyAttendanceDto;
import lk.ijse.poultryfarm.dto.tm.EmployeeAttendanceTm;
import lk.ijse.poultryfarm.dao.custom.impl.DailyAttendanceDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeAttendancePageController implements Initializable {
    public TableView<EmployeeAttendanceTm> tblEmployeeAttendance;
    public TableColumn<EmployeeAttendanceTm,String> colBatchId;
    public TableColumn<EmployeeAttendanceTm,String> colAttendanceId;
    public TableColumn<EmployeeAttendanceTm,String> colDate;
    public TableColumn<EmployeeAttendanceTm,String> colEmployeeId;
    public TableColumn<EmployeeAttendanceTm,Boolean> colAttendance;

    private final DailyAttendanceDAOImpl dailyAttendanceModel = new DailyAttendanceDAOImpl();
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnReset;
    public DatePicker searchDate;
    public JFXButton btnDelete;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnReset);
        ButtonScale.buttonScaling(btnDelete);

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
            btnDelete.setDisable(true);

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

    public void deleteAttendanceOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = dailyAttendanceModel.deleteAttendance(selectedAttendanceId);

                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Attendance deleted successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error in deleting Attendance").show();
                }
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Error in deleting Attendance").show();
            }
        }
    }

    public static String selectedAttendanceId;
    public static String selectedDate;

    public void onClickTable(MouseEvent mouseEvent) {
        try {
            EmployeeAttendanceTm selectedItem = tblEmployeeAttendance.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedAttendanceId = selectedItem.getAttendanceId();
                selectedDate = selectedItem.getDate();

                if(selectedDate.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }
}

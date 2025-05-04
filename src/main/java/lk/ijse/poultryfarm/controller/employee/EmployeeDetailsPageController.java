package lk.ijse.poultryfarm.controller.employee;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.dto.EmployeeDto;
import lk.ijse.poultryfarm.dto.tm.EmployeeDetailsTm;
import lk.ijse.poultryfarm.model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeDetailsPageController implements Initializable {
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnAdd;
    public TableView<EmployeeDetailsTm> tblEmployee;
    public TableColumn<EmployeeDetailsTm,String> colEmployeeId;
    public TableColumn<EmployeeDetailsTm,String> colName;
    public TableColumn<EmployeeDetailsTm,Boolean> colFullTime;
    public TableColumn<EmployeeDetailsTm,String> colContact;
    public TableColumn<EmployeeDetailsTm,Double> colDailyWage;

    private final EmployeeModel employeeModel = new EmployeeModel();
    public JFXButton btnSalary;
    public JFXButton btnAttendance;

    public static String selectedEmployeeId;

    public void searchEmployeeOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<EmployeeDto> employeeDtos = employeeModel.searchEmployee(inputSearch.getText());
            ObservableList<EmployeeDetailsTm> employeeDetailsTms = FXCollections.observableArrayList();
            for (EmployeeDto employeeDto : employeeDtos) {
                EmployeeDetailsTm employeeDetailsTm = new EmployeeDetailsTm(
                        employeeDto.getEmployeeId(),
                        employeeDto.getName(),
                        employeeDto.isFullTime(),
                        employeeDto.getContact(),
                        employeeDto.getDailyWage()
                );
                employeeDetailsTms.add(employeeDetailsTm);
            }
            tblEmployee.setItems(employeeDetailsTms);
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving employees").show();
        }
    }

    public void addEmployeeOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddEmployee.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add employee window").show();
        }
    }

    private void resetPage() {
        btnSalary.setDisable(true);
        btnAttendance.setDisable(true);
        btnAdd.setDisable(false);

        try {
            loadTableData();
            inputSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving employees").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDtos = employeeModel.getAllEmployee();
        ObservableList<EmployeeDetailsTm> employeeDetailsTms = FXCollections.observableArrayList();
        for (EmployeeDto employeeDto : employeeDtos) {
            EmployeeDetailsTm employeeDetailsTm = new EmployeeDetailsTm(
                    employeeDto.getEmployeeId(),
                    employeeDto.getName(),
                    employeeDto.isFullTime(),
                    employeeDto.getContact(),
                    employeeDto.getDailyWage()
            );
            employeeDetailsTms.add(employeeDetailsTm);
        }
        tblEmployee.setItems(employeeDetailsTms);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        btnSalary.setDisable(false);
        btnAttendance.setDisable(false);
        btnAdd.setDisable(true);

        try {
            EmployeeDetailsTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedEmployeeId = selectedItem.getEmployeeId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving selected item").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colFullTime.setCellValueFactory(new PropertyValueFactory<>("fullTime"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDailyWage.setCellValueFactory(new PropertyValueFactory<>("dailyWage"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving employees").show();
        }
    }

    public void AddSalaryOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddSalary.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add salary window").show();
        }
    }

    public void AddEmployeeAttendanceOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddDailyAttendance.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add daily attendance window").show();
        }
    }
}

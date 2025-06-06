package lk.ijse.poultryfarm.controller.employee;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.SalaryDto;
import lk.ijse.poultryfarm.dto.tm.SalaryManagementTm;
import lk.ijse.poultryfarm.model.EmployeeModel;
import lk.ijse.poultryfarm.model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalaryManagementPageController implements Initializable {
    public TableView<SalaryManagementTm> tblSalary;
    public TableColumn<SalaryManagementTm,String> colSalaryId;
    public TableColumn<SalaryManagementTm,String> colEmployeeId;
    public TableColumn<SalaryManagementTm,Double> colSalary;
    public TableColumn<SalaryManagementTm,String> colDate;

    private final SalaryModel salaryModel = new SalaryModel();
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnReset;
    public JFXComboBox<String> searchEmployeeName;
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

        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving salary management").show();
        }
    }

    private void resetPage() {
        try {
            inputSearch.clear();
            btnSearch.setDisable(true);
            btnDelete.setDisable(true);

            EmployeeModel employeeModel = new EmployeeModel();
            searchEmployeeName.getItems().clear();
            searchEmployeeName.setItems(employeeModel.getAllEmployeeNames());

            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving salary management").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<SalaryDto> salaryDtos = salaryModel.getAllSalary();
        ObservableList<SalaryManagementTm> salaryManagementTms = FXCollections.observableArrayList();
        for (SalaryDto salaryDto : salaryDtos) {
            SalaryManagementTm salaryManagementTm = new SalaryManagementTm(
                    salaryDto.getSalaryId(),
                    salaryDto.getEmployeeId(),
                    salaryDto.getAmount(),
                    salaryDto.getDate()
            );
            salaryManagementTms.add(salaryManagementTm);
        }
        tblSalary.setItems(salaryManagementTms);
    }

    public void searchSalaryOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<SalaryDto> salaryDtos = salaryModel.searchSalary(inputSearch.getText());
            ObservableList<SalaryManagementTm> salaryManagementTms = FXCollections.observableArrayList();
            for (SalaryDto salaryDto : salaryDtos) {
                SalaryManagementTm salaryManagementTm = new SalaryManagementTm(
                        salaryDto.getSalaryId(),
                        salaryDto.getEmployeeId(),
                        salaryDto.getAmount(),
                        salaryDto.getDate()
                );
                salaryManagementTms.add(salaryManagementTm);
            }
            tblSalary.setItems(salaryManagementTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving salary management").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchEmployeeNameOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSearch.setDisable(false);
        String name = searchEmployeeName.getSelectionModel().getSelectedItem();

        EmployeeModel employeeModel = new EmployeeModel();
        String employeeId = employeeModel.getEmployeeId(name);

        inputSearch.setText(employeeId);
    }

    public void deleteSalaryOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = salaryModel.deleteSalary(selectedSalaryId);

                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Salary Deleted successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error in Deleting Salary").show();
                }
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Error in Deleting Salary").show();
            }
        }
    }

    public static String selectedSalaryId;

    public void onClickTable(MouseEvent mouseEvent) {
        try {
            SalaryManagementTm selectedItem = tblSalary.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedSalaryId = selectedItem.getSalaryId();
                btnDelete.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }
}

package lk.ijse.poultryfarm.controller.employee;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.SalaryDto;
import lk.ijse.poultryfarm.dto.tm.SalaryManagementTm;
import lk.ijse.poultryfarm.model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public JFXButton btnDelete;
    public JFXButton btnUpdate;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnDelete);
        ButtonScale.buttonScaling(btnUpdate);
        ButtonScale.buttonScaling(btnSearch);

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

    public void onClickTable(MouseEvent mouseEvent) {
    }

    public void deleteSalaryOnAction(ActionEvent actionEvent) {
    }

    public void updateSalaryOnAction(ActionEvent actionEvent) {
    }
}

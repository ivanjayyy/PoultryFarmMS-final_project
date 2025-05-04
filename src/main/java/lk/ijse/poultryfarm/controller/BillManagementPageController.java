package lk.ijse.poultryfarm.controller;

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
import lk.ijse.poultryfarm.dto.BillDto;
import lk.ijse.poultryfarm.dto.tm.BillManagementTm;
import lk.ijse.poultryfarm.model.BillModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BillManagementPageController implements Initializable {
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnAddBill;

    public TableView<BillManagementTm> tblBill;
    public TableColumn<BillManagementTm,String> colBatchId;
    public TableColumn<BillManagementTm,String> colBillId;
    public TableColumn<BillManagementTm,String> colBillVariant;
    public TableColumn<BillManagementTm,Double> colPaidAmount;
    public TableColumn<BillManagementTm,String> colPaidDate;

    public JFXButton btnDelete;
    public JFXButton btnUpdate;

    private final BillModel billModel = new BillModel();

    public void searchBillOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<BillDto> billDtos = billModel.searchBill(inputSearch.getText());
            ObservableList<BillManagementTm> billManagementTms = FXCollections.observableArrayList();
            for (BillDto billDto : billDtos) {
                BillManagementTm billManagementTm = new BillManagementTm(
                        billDto.getBatchId(),
                        billDto.getBillId(),
                        billDto.getBillVariant(),
                        billDto.getAmount(),
                        billDto.getDate()
                );
                billManagementTms.add(billManagementTm);
            }
            tblBill.setItems(billManagementTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    public void DeleteBillOnAction(ActionEvent actionEvent) {
    }

    public void updateBillOnAction(ActionEvent actionEvent) {
    }

    public void addBillOnAction(ActionEvent actionEvent) throws IOException {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddBill.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add bill window").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnAddBill);
        ButtonScale.buttonScaling(btnSearch);

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colBillVariant.setCellValueFactory(new PropertyValueFactory<>("billVariant"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaidDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try{
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            inputSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<BillDto> billDtos = billModel.getAllBill();
        ObservableList<BillManagementTm> billManagementTms = FXCollections.observableArrayList();
        for (BillDto billDto : billDtos) {
            BillManagementTm billManagementTm = new BillManagementTm(
                    billDto.getBatchId(),
                    billDto.getBillId(),
                    billDto.getBillVariant(),
                    billDto.getAmount(),
                    billDto.getDate()
            );
            billManagementTms.add(billManagementTm);
        }
        tblBill.setItems(billManagementTms);
    }
}

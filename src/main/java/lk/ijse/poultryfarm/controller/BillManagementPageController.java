package lk.ijse.poultryfarm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Optional;
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

    public static String selectedBatchId;
    public static String selectedBillId;
    public static String selectedBillVariant;
    public static double selectedBillAmount;
    public static String selectedBillDate;

    public void onClickTable(MouseEvent mouseEvent) {
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        btnAddBill.setDisable(true);

        try {
            BillManagementTm selectedItem = tblBill.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedBatchId = selectedItem.getBatchId();
                selectedBillId = selectedItem.getBillId();
                selectedBillVariant = selectedItem.getBillVariant();
                selectedBillAmount = selectedItem.getAmount();
                selectedBillDate = selectedItem.getDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }

    public void DeleteBillOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = billModel.deleteBill(selectedBillId);

                if(isDeleted){
                    new Alert(Alert.AlertType.INFORMATION,"Bill deleted successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error in deleting bill").show();
                }
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Error in deleting bill").show();
            }
        }
    }

    public static boolean updateBill;

    public void updateBillOnAction(ActionEvent actionEvent) {
        updateBill = true;

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddBill.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add bill window").show();
        }

        updateBill = false;
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
        ButtonScale.buttonScaling(btnDelete);
        ButtonScale.buttonScaling(btnUpdate);

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
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
            btnAddBill.setDisable(false);

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

package lk.ijse.poultryfarm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.poultryfarm.dao.custom.impl.BillDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.ChickBatchDAOImpl;

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

    private final BillDAOImpl billModel = new BillDAOImpl();
    public Label lblWaterBillStatus;
    public Label lblElecBillStatus;
    public JFXButton btnReset;
    public JFXComboBox<String> searchBillType;

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

    public void onClickTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        try {
            BillManagementTm selectedItem = tblBill.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedBatchId = selectedItem.getBatchId();
                selectedBillId = selectedItem.getBillId();
                selectedBillVariant = selectedItem.getBillVariant();
                selectedBillAmount = selectedItem.getAmount();
                selectedBillDate = selectedItem.getDate();

                ChickBatchDAOImpl chickBatchModel = new ChickBatchDAOImpl();
                String currentBatchId = chickBatchModel.getCurrentBatchId();

                if(selectedBatchId.equals(currentBatchId)){
                    btnDelete.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                }

                btnAddBill.setDisable(true);
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
        ButtonScale.buttonScaling(btnReset);

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
            btnAddBill.setDisable(false);
            btnSearch.setDisable(true);

            searchBillType.getItems().clear();
            searchBillType.getItems().addAll("Water","Electricity");

            loadData();

            loadTableData();
            inputSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving bills").show();
        }
    }

    private void loadData() throws SQLException, ClassNotFoundException {
        ChickBatchDAOImpl chickBatchModel = new ChickBatchDAOImpl();

        String currentBatchId = chickBatchModel.getCurrentBatchId();
        int waterBillStatus = billModel.billPaidStatus(currentBatchId,"Water");

        if(waterBillStatus == 1){
            lblWaterBillStatus.setText("Paid");
        }else if(waterBillStatus == 0){
            lblWaterBillStatus.setText("Not Paid");
        } else {
            new Alert(Alert.AlertType.ERROR,"Error in retrieving water bill status").show();
        }

        int elecBillStatus = billModel.billPaidStatus(currentBatchId,"Electricity");

        if(elecBillStatus == 1){
            lblElecBillStatus.setText("Paid");
        }else if(elecBillStatus == 0){
            lblElecBillStatus.setText("Not Paid");
        } else {
            new Alert(Alert.AlertType.ERROR, "Error in retrieving electricity bill status").show();
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

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchBillTypeOnAction(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        String billType = searchBillType.getSelectionModel().getSelectedItem();
        inputSearch.setText(billType);
    }
}

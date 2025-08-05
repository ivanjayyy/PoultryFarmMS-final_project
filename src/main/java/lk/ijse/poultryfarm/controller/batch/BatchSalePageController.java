package lk.ijse.poultryfarm.controller.batch;

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
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.dto.tm.BatchSaleTm;
import lk.ijse.poultryfarm.dao.custom.impl.ChickBatchDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.SaleDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BatchSalePageController implements Initializable {
    public static String selectedBatchId;
    public static String selectedBatchDate;
    public static String selectedBatchTotalSale;
    public static String selectedSaleId;
    public static boolean updateSale;
    public static String selectedBatchChicksSold;

    public TableView<BatchSaleTm> tblSale;
    public TableColumn<BatchSaleTm,String> colBatchId;
    public TableColumn<BatchSaleTm,String> colSaleId;
    public TableColumn<BatchSaleTm,Double> colAmount;
    public TableColumn<BatchSaleTm,String> colDate;
    public TableColumn<BatchSaleTm,Integer> colChicksSold;

    private final SaleDAOImpl saleModel = new SaleDAOImpl();

    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnReset;
    public JFXComboBox<String> searchBatchId;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnDelete);
        ButtonScale.buttonScaling(btnUpdate);
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnReset);

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colChicksSold.setCellValueFactory(new PropertyValueFactory<>("chicksSold"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void resetPage() {
        try {
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
            btnSearch.setDisable(true);
            loadTableData();
            inputSearch.clear();

            ChickBatchDAOImpl chickBatchModel = new ChickBatchDAOImpl();
            searchBatchId.getItems().clear();
            searchBatchId.setItems(chickBatchModel.getAllBatchIds());

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<SaleDto> saleDtos = saleModel.getAllSale();
        ObservableList<BatchSaleTm> batchSaleTms = FXCollections.observableArrayList();
        for (SaleDto saleDto : saleDtos) {
            BatchSaleTm batchSaleTm = new BatchSaleTm(
                    saleDto.getBatchId(),
                    saleDto.getSaleId(),
                    saleDto.getTotalSale(),
                    saleDto.getDate(),
                    saleDto.getChicksSold()
            );
            batchSaleTms.add(batchSaleTm);
        }
        tblSale.setItems(batchSaleTms);
    }

    public void batchSaleSearchOnAction(ActionEvent actionEvent) {
        try{
            ArrayList<SaleDto> saleDtos = saleModel.searchSale(inputSearch.getText());
            ObservableList<BatchSaleTm> batchSaleTms = FXCollections.observableArrayList();
            for (SaleDto saleDto : saleDtos) {
                BatchSaleTm batchSaleTm = new BatchSaleTm(
                        saleDto.getBatchId(),
                        saleDto.getSaleId(),
                        saleDto.getTotalSale(),
                        saleDto.getDate(),
                        saleDto.getChicksSold()
                );
                batchSaleTms.add(batchSaleTm);
            }
            tblSale.setItems(batchSaleTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        try{
            BatchSaleTm selectedItem = tblSale.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selectedBatchId = selectedItem.getBatchId();
                selectedBatchDate = selectedItem.getDate();
                selectedBatchTotalSale = String.valueOf(selectedItem.getTotalSale());
                selectedSaleId = selectedItem.getSaleId();
                selectedBatchChicksSold = String.valueOf(selectedItem.getChicksSold());

                if(selectedBatchDate.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
                    btnDelete.setDisable(false);
                    btnUpdate.setDisable(false);
                } else {
                    btnDelete.setDisable(true);
                    btnUpdate.setDisable(true);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void deleteBatchSaleOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = saleModel.deleteSale(selectedSaleId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION,"Batch sale deleted successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Error in deleting batch sale").show();
                }
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Error in deleting batch sale").show();
            }
        }
    }

    public void updateBatchSaleOnAction(ActionEvent actionEvent) {
        updateSale = true;
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddSale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add sale window").show();
        }
        updateSale = false;
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchBatchIdOnAction(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        String batchId = searchBatchId.getSelectionModel().getSelectedItem();
        inputSearch.setText(batchId);
    }
}

package lk.ijse.poultryfarm.controller.batch;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.dto.tm.BatchSaleTm;
import lk.ijse.poultryfarm.model.SaleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BatchSalePageController implements Initializable {
    public TableView<BatchSaleTm> tblSale;
    public TableColumn<BatchSaleTm,String> colBatchId;
    public TableColumn<BatchSaleTm,String> colSaleId;
    public TableColumn<BatchSaleTm,Double> colAmount;
    public TableColumn<BatchSaleTm,String> colDate;

    private final SaleModel saleModel = new SaleModel();
    public TextField inputSearch;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();

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
                    saleDto.getDate()
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
                        saleDto.getDate()
                );
                batchSaleTms.add(batchSaleTm);
            }
            tblSale.setItems(batchSaleTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }
}

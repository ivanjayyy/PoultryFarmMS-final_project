package lk.ijse.poultryfarm.controller.batch;

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
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.dto.ChickStatusDto;
import lk.ijse.poultryfarm.dto.tm.BatchStatusTm;
import lk.ijse.poultryfarm.model.ChickStatusModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BatchStatusPageController implements Initializable {
    public TableView<BatchStatusTm> tblBatchStatus;
    public TableColumn<BatchStatusTm,String> colBatchId;
    public TableColumn<BatchStatusTm,String> colStatusId;
    public TableColumn<BatchStatusTm,String> colDate;
    public TableColumn<BatchStatusTm,Integer> colChickDeaths;

    private final ChickStatusModel chickStatusModel = new ChickStatusModel();
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

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colStatusId.setCellValueFactory(new PropertyValueFactory<>("chickStatusId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colChickDeaths.setCellValueFactory(new PropertyValueFactory<>("chicksDead"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void resetPage() {
        try{
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ChickStatusDto> chickBatchDtos = chickStatusModel.getAllChickStatus();
        ObservableList<BatchStatusTm> batchStatusTms = FXCollections.observableArrayList();

        for (ChickStatusDto chickStatusDto : chickBatchDtos) {
            BatchStatusTm batchStatusTm = new BatchStatusTm(
                    chickStatusDto.getBatchId(),
                    chickStatusDto.getChickStatusId(),
                    chickStatusDto.getDate(),
                    chickStatusDto.getChicksDead()
            );
            batchStatusTms.add(batchStatusTm);
        }
        tblBatchStatus.setItems(batchStatusTms);
    }

    public void batchStatusSearchOnAction(ActionEvent actionEvent) {
        try{
            ArrayList<ChickStatusDto> chickBatchDtos = chickStatusModel.searchChickStatus(inputSearch.getText());
            ObservableList<BatchStatusTm> batchStatusTms = FXCollections.observableArrayList();

            for (ChickStatusDto chickStatusDto : chickBatchDtos) {
                BatchStatusTm batchStatusTm = new BatchStatusTm(
                        chickStatusDto.getBatchId(),
                        chickStatusDto.getChickStatusId(),
                        chickStatusDto.getDate(),
                        chickStatusDto.getChicksDead()
                );
                batchStatusTms.add(batchStatusTm);
            }
            tblBatchStatus.setItems(batchStatusTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {

    }

    public void deleteBatchStatusOnAction(ActionEvent actionEvent) {
    }

    public void updateBatchStatusOnAction(ActionEvent actionEvent) {
    }
}

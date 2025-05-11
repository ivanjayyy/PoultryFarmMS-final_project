package lk.ijse.poultryfarm.controller.batch;

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
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.dto.ChickStatusDto;
import lk.ijse.poultryfarm.dto.tm.BatchStatusTm;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.ChickStatusModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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

    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

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
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

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

    public static String selectedBatchId;
    public static String selectedBatchStatusId;
    public static String selectedBatchDate;
    public static int selectedBatchChickDeaths;
    public static boolean updateStatus;

    public void onClickTable(MouseEvent mouseEvent) {
        BatchStatusTm selectedItem = tblBatchStatus.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedBatchId = selectedItem.getBatchId();
            selectedBatchStatusId = selectedItem.getChickStatusId();
            selectedBatchDate = selectedItem.getDate();
            selectedBatchChickDeaths = selectedItem.getChicksDead();

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void deleteBatchStatusOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (selectedBatchId.equals(chickBatchModel.getCurrentBatchId())){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean isDeleted = chickStatusModel.deleteChickStatus(selectedBatchStatusId);

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Batch status deleted successfully").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Error in deleting batch status").show();
                    }
                    resetPage();
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error in deleting batch status").show();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "You can't delete old batch status").show();
        }
    }

    public void updateBatchStatusOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        updateStatus = true;

        String currentBatchId = chickBatchModel.getCurrentBatchId();
        if(selectedBatchId.equals(currentBatchId)){
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddChickStatus.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error in opening add chick status window").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "You can't update old batch status").show();
        }
        updateStatus = false;
    }
}

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
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.dto.tm.BatchDetailsTm;
import lk.ijse.poultryfarm.model.ChickBatchModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BatchDetailsPageController implements Initializable {
    public JFXButton btnSearch;
    public JFXButton btnAdd;

    public TextField inputSearch;
    public Label lblTotalBatches;
    public Label lblChicksLeft;
    public Label lblChicksDead;

    public TableView<BatchDetailsTm> tblBatchDetails;
    public TableColumn<BatchDetailsTm,String> colBatchId;
    public TableColumn<BatchDetailsTm,Integer> colTotalChicks;
    public TableColumn<BatchDetailsTm,Double> colPaymentMade;
    public TableColumn<BatchDetailsTm,String> colArrivedDate;

    private final ChickBatchModel chickBatchModel = new ChickBatchModel();
    public JFXButton btnSale;
    public JFXButton btnStatus;


    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colTotalChicks.setCellValueFactory(new PropertyValueFactory<>("chickTotal"));
        colPaymentMade.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colArrivedDate.setCellValueFactory(new PropertyValueFactory<>("date"));

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

            inputSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ChickBatchDto> chickBatchDtos = chickBatchModel.getAllChickBatch();
        ObservableList<BatchDetailsTm> batchDetailsTms = FXCollections.observableArrayList();

        for (ChickBatchDto chickBatchDto : chickBatchDtos) {
            BatchDetailsTm batchDetailsTm = new BatchDetailsTm(
                    chickBatchDto.getBatchId(),
                    chickBatchDto.getChickTotal(),
                    chickBatchDto.getPayment(),
                    chickBatchDto.getDate()
            );
            batchDetailsTms.add(batchDetailsTm);
        }
        tblBatchDetails.setItems(batchDetailsTms);
    }

    public void searchBatchOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<ChickBatchDto> chickBatchDtos = chickBatchModel.searchChickBatch(inputSearch.getText());
            ObservableList<BatchDetailsTm> batchDetailsTms = FXCollections.observableArrayList();

            for (ChickBatchDto chickBatchDto : chickBatchDtos) {
                BatchDetailsTm batchDetailsTm = new BatchDetailsTm(
                        chickBatchDto.getBatchId(),
                        chickBatchDto.getChickTotal(),
                        chickBatchDto.getPayment(),
                        chickBatchDto.getDate()
                );
                batchDetailsTms.add(batchDetailsTm);
            }
            tblBatchDetails.setItems(batchDetailsTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void addBatchOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddChickBatch.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add batch window").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
    }

    public void addBatchSaleOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddSale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add batch sale window").show();
        }
    }

    public void addChickStatusOnAction(ActionEvent actionEvent) {
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
            new Alert(Alert.AlertType.ERROR,"Error in opening add chick status window").show();
        }
    }
}

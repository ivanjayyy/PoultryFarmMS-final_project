package lk.ijse.poultryfarm.controller.add;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.batch.BatchDetailsPageController;
import lk.ijse.poultryfarm.controller.batch.BatchSalePageController;
import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.SaleModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddSaleController implements Initializable {
    public Label lblBatchId;
    public Label lblSaleId;
    public TextField inputTotalSale;
    public DatePicker inputSoldDate;
    public JFXButton btnSave;

    private final SaleModel saleModel = new SaleModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    public void saveBatchSaleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String saleId = lblSaleId.getText();
        String totalSale = inputTotalSale.getText();
        String date = inputSoldDate.getValue().toString();

        SaleDto saleDto = new SaleDto(batchId,saleId,Double.parseDouble(totalSale),date);

        if(BatchSalePageController.updateSale){
            boolean isUpdated = saleModel.updateSale(saleDto);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Sale Updated Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }else {
                new Alert(Alert.AlertType.ERROR,"Sale Update Failed").show();
            }
        } else {
            boolean isSaved = saleModel.saveSale(saleDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION,"Sale Saved Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR,"Sale Save Failed").show();
            }
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            btnSave.setText("SAVE");
            inputSoldDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            ButtonScale.buttonScaling(btnSave);

            if(BatchSalePageController.updateSale){
                lblBatchId.setText(BatchSalePageController.selectedBatchId);
                lblSaleId.setText(BatchSalePageController.selectedSaleId);
                inputSoldDate.setValue(LocalDate.parse(BatchSalePageController.selectedBatchDate));
                inputTotalSale.setText(String.valueOf(BatchSalePageController.selectedBatchTotalSale));

                btnSave.setText("UPDATE");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving customer id").show();
        }
    }

    private void loadBatchId() throws SQLException, ClassNotFoundException {
        String currentBatchId = chickBatchModel.getCurrentBatchId();

        if (currentBatchId != null) {
            lblBatchId.setText(currentBatchId);
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = saleModel.getNextSaleId();
        lblSaleId.setText(nextId);
    }
}

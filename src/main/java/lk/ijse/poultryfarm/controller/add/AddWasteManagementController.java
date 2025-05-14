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
import lk.ijse.poultryfarm.controller.WasteManagementPageController;
import lk.ijse.poultryfarm.dto.WasteManagementDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.WasteManagementModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddWasteManagementController implements Initializable {
    public Label lblBatchId;
    public Label lblWasteId;
    public TextField inputTotalSale;
    public DatePicker inputSoldDate;
    public JFXButton btnSave;

    private final WasteManagementModel wasteManagementModel = new WasteManagementModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String wasteId = lblWasteId.getText();
        String totalSale = inputTotalSale.getText();
        String date = inputSoldDate.getValue().toString();

        WasteManagementDto wasteManagementDto = new WasteManagementDto(batchId,wasteId,Double.parseDouble(totalSale),date);

        if(WasteManagementPageController.updateWaste){
            boolean isUpdate = wasteManagementModel.updateWasteManagement(wasteManagementDto);

            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Waste Updated Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }else {
                new Alert(Alert.AlertType.ERROR,"Waste Update Failed").show();
            }
        } else {
            boolean isSaved = wasteManagementModel.saveWasteManagement(wasteManagementDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Waste Saved Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Waste Save Failed").show();
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

            if(WasteManagementPageController.updateWaste){
                lblBatchId.setText(WasteManagementPageController.selectedBatchId);
                lblWasteId.setText(WasteManagementPageController.selectedWasteId);
                inputSoldDate.setValue(LocalDate.parse(WasteManagementPageController.selectedWasteDate));
                inputTotalSale.setText(String.valueOf(WasteManagementPageController.selectedWasteAmount));

                btnSave.setText("UPDATE");
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Error in retrieving customer id").show();
            e.printStackTrace();
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
        String nextId = wasteManagementModel.getNextWasteId();
        lblWasteId.setText(nextId);
    }
}

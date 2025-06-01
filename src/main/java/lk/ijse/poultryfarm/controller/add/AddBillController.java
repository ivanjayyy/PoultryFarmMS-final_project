package lk.ijse.poultryfarm.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.BillManagementPageController;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.BillDto;
import lk.ijse.poultryfarm.model.BillModel;
import lk.ijse.poultryfarm.model.ChickBatchModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddBillController implements Initializable {
    public JFXComboBox<String> lblBatchId;
    public Label lblBillId;
    public TextField inputPaidAmount;
    public DatePicker inputPaidDate;
    public JFXButton btnSave;
    public JFXComboBox<String> inputBillVariant;

    private final String patternPaidAmount = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final BillModel billModel = new BillModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getValue();
        String billId = lblBillId.getText();
        String billVariant = inputBillVariant.getValue();
        String paidAmount = inputPaidAmount.getText();
        String paidDate = inputPaidDate.getValue().toString();

        boolean isValidPaidAmount = paidAmount.matches(patternPaidAmount);

        inputPaidAmount.setStyle("-fx-text-inner-color: black;");

        if(!isValidPaidAmount){
            inputPaidAmount.setStyle("-fx-text-inner-color: red;");
        }

        if(!isValidPaidAmount){
            new Alert(Alert.AlertType.ERROR,"Invalid Input.").show();
            return;
        }

        BillDto billDto = new BillDto(batchId,billId,billVariant,Double.parseDouble(paidAmount),paidDate);

        if(BillManagementPageController.updateBill){
            boolean isUpdated = billModel.updateBill(billDto);
            if(!isUpdated){
                new Alert(Alert.AlertType.ERROR,"Bill Update Failed").show();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Bill Updated Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }

        } else {
            boolean isSaved = billModel.saveBill(billDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Bill Saved Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Bill Save Failed").show();
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
            inputBillVariant.getItems().addAll("Electricity","Water");
            inputBillVariant.setValue("Electricity");
            inputPaidDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            ButtonScale.buttonScaling(btnSave);

            btnSave.setText("SAVE");

            if(BillManagementPageController.updateBill){
                lblBatchId.setValue(BillManagementPageController.selectedBatchId);
                lblBillId.setText(BillManagementPageController.selectedBillId);
                inputPaidDate.setValue(LocalDate.parse(BillManagementPageController.selectedBillDate));
                inputBillVariant.setValue(BillManagementPageController.selectedBillVariant);
                inputPaidAmount.setText(String.valueOf(BillManagementPageController.selectedBillAmount));

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
            lblBatchId.setValue(currentBatchId);
            lblBatchId.setItems(chickBatchModel.getAllBatchIds());
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }

    public void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = billModel.getNextBillId();
        lblBillId.setText(nextId);
    }
}

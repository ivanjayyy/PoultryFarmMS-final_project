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
import lk.ijse.poultryfarm.dto.BillDto;
import lk.ijse.poultryfarm.model.BillModel;
import lk.ijse.poultryfarm.model.ChickBatchModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBillController implements Initializable {
    public Label lblBatchId;
    public Label lblBillId;
    public TextField inputPaidAmount;
    public DatePicker inputPaidDate;
    public JFXButton btnSave;
    public JFXComboBox<String> inputBillVariant;

    private final BillModel billModel = new BillModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String billId = lblBillId.getText();
        String billVariant = inputBillVariant.getValue();
        String paidAmount = inputPaidAmount.getText();
        String paidDate = inputPaidDate.getValue().toString();

        BillDto billDto = new BillDto(batchId,billId,billVariant,Double.parseDouble(paidAmount),paidDate);

        boolean isSaved = billModel.saveBill(billDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Bill Saved Successfully").show();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }else {
            new Alert(Alert.AlertType.ERROR, "Bill Save Failed").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputBillVariant.getItems().addAll("Current","Water");
            loadNextId();
            loadBatchId();
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

    public void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = billModel.getNextBillId();
        lblBillId.setText(nextId);
    }
}

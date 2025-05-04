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
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddChickBatchController implements Initializable {
    public Label lblBatchId;
    public TextField inputTotalChicks;
    public TextField inputPaymentMade;
    public JFXButton btnSave;
    public DatePicker inputArrivedDate;

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String totalChicks = inputTotalChicks.getText();
        String paymentMade = inputPaymentMade.getText();
        String arrivedDate = inputArrivedDate.getValue().toString();

        ChickBatchDto chickBatchDto = new ChickBatchDto(batchId, Integer.parseInt(totalChicks),Double.parseDouble(paymentMade),arrivedDate);

        boolean isSaved = chickBatchModel.saveChickBatch(chickBatchDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION,"Batch Saved Successfully").show();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR,"Batch Save Failed").show();
        }
    }

    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputArrivedDate.setValue(java.time.LocalDate.now());
            loadNextId();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving customer id").show();
        }
    }

    public void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = chickBatchModel.getNextBatchId();
        lblBatchId.setText(nextId);
    }
}

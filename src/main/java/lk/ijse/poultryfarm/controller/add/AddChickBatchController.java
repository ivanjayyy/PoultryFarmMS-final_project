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
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddChickBatchController implements Initializable {
    public Label lblBatchId;
    public TextField inputTotalChicks;
    public TextField inputPaymentMade;
    public JFXButton btnSave;
    public DatePicker inputArrivedDate;

    private final String patternTotalChicks = "^[0-9]+$";
    private final String patternPaymentMade = "^[0-9]+(\\.[0-9]{1,2})?$";

    public void saveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String totalChicks = inputTotalChicks.getText();
        String paymentMade = inputPaymentMade.getText();
        String arrivedDate = inputArrivedDate.getValue().toString();

        boolean isValidTotalChicks = totalChicks.matches(patternTotalChicks);
        boolean isValidPaymentMade = paymentMade.matches(patternPaymentMade);

        inputTotalChicks.setStyle("-fx-text-inner-color: black");
        inputPaymentMade.setStyle("-fx-text-inner-color: black");

        if(!isValidTotalChicks){
            inputTotalChicks.setStyle("-fx-text-inner-color: red");
        }

        if(!isValidPaymentMade){
            inputPaymentMade.setStyle("-fx-text-inner-color: red");
        }

        if(!isValidTotalChicks || !isValidPaymentMade){
            new Alert(Alert.AlertType.ERROR,"Invalid Input.").show();
            return;
        }

        ChickBatchDto chickBatchDto = new ChickBatchDto(batchId, Integer.parseInt(totalChicks),Double.parseDouble(paymentMade),arrivedDate);

        if(BatchDetailsPageController.updateChickBatch){
            boolean isUpdated = chickBatchModel.updateChickBatch(chickBatchDto);

            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Batch Updated Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

            }else{
                new Alert(Alert.AlertType.ERROR,"Batch Update Failed").show();
            }

        } else {
            boolean isSaved = chickBatchModel.saveChickBatch(chickBatchDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION,"Batch Saved Successfully").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR,"Batch Save Failed").show();
            }
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
            ButtonScale.buttonScaling(btnSave);
            btnSave.setText("SAVE");

            inputArrivedDate.setValue(java.time.LocalDate.now());
            loadNextId();

            if(BatchDetailsPageController.updateChickBatch){
                lblBatchId.setText(BatchDetailsPageController.selectedBatchId);
                inputTotalChicks.setText(String.valueOf(BatchDetailsPageController.selectedBatchTotalChicks));
                inputArrivedDate.setValue(LocalDate.parse(BatchDetailsPageController.selectedBatchDate));
                inputPaymentMade.setText(String.valueOf(BatchDetailsPageController.selectedBatchPayment));

                btnSave.setText("UPDATE");
            }
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

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
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
        AtomicBoolean totalChicks = new AtomicBoolean(false);
        AtomicBoolean paymentMade = new AtomicBoolean(false);
        btnSave.setDisable(true);

        try {
            inputTotalChicks.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternTotalChicks)) {
                    inputTotalChicks.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    totalChicks.set(true);

                    if(paymentMade.get()) {
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputTotalChicks.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    totalChicks.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputTotalChicks.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    totalChicks.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputPaymentMade.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternPaymentMade)) {
                    inputPaymentMade.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    paymentMade.set(true);

                    if(totalChicks.get()) {
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputPaymentMade.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    paymentMade.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputPaymentMade.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    paymentMade.set(false);
                    btnSave.setDisable(true);
                }
            });

            ButtonScale.buttonScaling(btnSave);

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

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
import lk.ijse.poultryfarm.dto.ChickStatusDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.ChickStatusModel;
import lk.ijse.poultryfarm.model.SaleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddChickStatusController implements Initializable {
    public Label lblBatchId;
    public Label lblChickStatusId;
    public DatePicker inputCheckedDate;
    public TextField inputChicksDead;
    public JFXButton btnSave;

    private final ChickStatusModel chickStatusModel = new ChickStatusModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    private final String patternChicksDead = "^[0-9]+$";

    public void saveChickStatusOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String chickStatusId = lblChickStatusId.getText();
        String checkedDate = inputCheckedDate.getValue().toString();
        String chicksDead = inputChicksDead.getText();

        int sumOfChickDead = chickStatusModel.selectedBatchChickDeaths(batchId);
        int chickDeadToday = Integer.parseInt(chicksDead);
        int batchChickTotal = chickBatchModel.getChickTotal(batchId);

        SaleModel saleModel = new SaleModel();
        int totalSold = saleModel.selectedBatchTotalSold(batchId);

        boolean isValid = (chickDeadToday + sumOfChickDead) <= (batchChickTotal-totalSold);

        int todayChickStatusCheckedCount = chickStatusModel.checkStatus(checkedDate,batchId);

        if(todayChickStatusCheckedCount != 0) {
            new Alert(Alert.AlertType.ERROR, "Error: You have already checked today's chick status.").show();
            return;
        }

                ChickStatusDto chickStatusDto = new ChickStatusDto(batchId,chickStatusId,checkedDate,Integer.parseInt(chicksDead));

                    if(!isValid) {
                        new Alert(Alert.AlertType.ERROR, "Error: Total Chicks Dead cannot exceed the total chicks in the batch.").show();
                        return;
                    }

                    boolean isSaved = chickStatusModel.saveChickStatus(chickStatusDto);

                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Chick Status Saved Successfully").show();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Chick Status Save Failed").show();
                    }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSave.setDisable(true);

        try {
            inputChicksDead.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternChicksDead)) {
                    inputChicksDead.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(false);

                } else if (newVal.isEmpty()) {
                    inputChicksDead.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(true);

                } else {
                    inputChicksDead.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(true);
                }
            });

            btnSave.setText("SAVE");
            inputCheckedDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            ButtonScale.buttonScaling(btnSave);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving customer id").show();
        }
    }

    private void loadBatchId() throws SQLException, ClassNotFoundException {
        String batchId = BatchDetailsPageController.selectedBatchId;

        if (batchId != null) {
            lblBatchId.setText(batchId);
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = chickStatusModel.getNextChickStatusId();
        lblChickStatusId.setText(nextId);
    }
}

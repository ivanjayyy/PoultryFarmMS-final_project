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
import lk.ijse.poultryfarm.controller.batch.BatchStatusPageController;
import lk.ijse.poultryfarm.dto.ChickStatusDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.ChickStatusModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddChickStatusController implements Initializable {
    public Label lblBatchId;
    public Label lblChickStatusId;
    public DatePicker inputCheckedDate;
    public TextField inputChicksDead;
    public JFXButton btnSave;

    private final ChickStatusModel chickStatusModel = new ChickStatusModel();
    private final ChickBatchModel chickBatchModel = new ChickBatchModel();

    public void saveChickStatusOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String chickStatusId = lblChickStatusId.getText();
        String checkedDate = inputCheckedDate.getValue().toString();
        String chicksDead = inputChicksDead.getText();

        int sumOfChickDead = chickStatusModel.selectedBatchChickDeaths(batchId);
        int chickDeadToday = Integer.parseInt(chicksDead);
        int batchChickTotal = chickBatchModel.getChickTotal(batchId);

        boolean isValid = (chickDeadToday + sumOfChickDead) <= batchChickTotal;

        int todayChickStatusCheckedCount = chickStatusModel.checkStatus(checkedDate);

        if(todayChickStatusCheckedCount == 0){
            if(isValid) {
                ChickStatusDto chickStatusDto = new ChickStatusDto(batchId,chickStatusId,checkedDate,Integer.parseInt(chicksDead));

                if(BatchStatusPageController.updateStatus){
                    boolean isUpdated = chickStatusModel.updateChickStatus(chickStatusDto);

                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "Chick Status Updated Successfully").show();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Chick Status Update Failed").show();
                    }
                } else {
                    boolean isSaved = chickStatusModel.saveChickStatus(chickStatusDto);

                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Chick Status Saved Successfully").show();
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Chick Status Save Failed").show();
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR,"Error: Total Chicks Dead cannot exceed the total chicks in the batch.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Error: You have already checked today's chick status.").show();
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
            inputCheckedDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            ButtonScale.buttonScaling(btnSave);

            if(BatchStatusPageController.updateStatus){
                lblBatchId.setText(BatchStatusPageController.selectedBatchId);
                lblChickStatusId.setText(BatchStatusPageController.selectedBatchStatusId);
                inputCheckedDate.setValue(LocalDate.parse(BatchStatusPageController.selectedBatchDate));
                inputChicksDead.setText(String.valueOf(BatchStatusPageController.selectedBatchChickDeaths));

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
        String nextId = chickStatusModel.getNextChickStatusId();
        lblChickStatusId.setText(nextId);
    }
}

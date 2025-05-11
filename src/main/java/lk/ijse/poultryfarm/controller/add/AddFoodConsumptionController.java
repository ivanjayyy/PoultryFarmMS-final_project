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
import lk.ijse.poultryfarm.controller.food.FoodInventoryPageController;
import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.FoodConsumptionModel;
import lk.ijse.poultryfarm.model.FoodModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddFoodConsumptionController implements Initializable {
    public Label lblBatchId;
    public Label lblConsumptionId;
    public DatePicker inputDate;
    public Label lblFoodId;
    public TextField inputConsumption;
    public JFXButton btnSave;

    private final ChickBatchModel chickBatchModel = new ChickBatchModel();
    private final FoodConsumptionModel foodConsumptionModel = new FoodConsumptionModel();
    private final FoodModel foodModel = new FoodModel();

    public void saveFoodConsumptionOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String consumptionId = lblConsumptionId.getText();
        String date = inputDate.getValue().toString();
        String foodId = lblFoodId.getText();
        String consumption = inputConsumption.getText();

        FoodConsumptionDto foodConsumptionDto = new FoodConsumptionDto(
                batchId,
                consumptionId,
                date,
                foodId,
                Double.parseDouble(consumption)
        );

        boolean isSaved = foodConsumptionModel.saveFoodConsumption(foodConsumptionDto);
        if (isSaved) {
            boolean isChanged = foodModel.updateAfterFoodConsumption(foodConsumptionDto);
            if(!isChanged){
                new Alert(Alert.AlertType.ERROR,"Food Consumption Failed").show();
            }

            new Alert(Alert.AlertType.INFORMATION,"Food Consumption Saved").show();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR,"Food Consumption Failed").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            lblFoodId.setText(FoodInventoryPageController.globalFoodId);
            ButtonScale.textFieldScaling(inputConsumption);
            ButtonScale.buttonScaling(btnSave);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Food Consumption Failed").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = foodConsumptionModel.getNextConsumptionId();
        lblConsumptionId.setText(nextId);
    }

    private void loadBatchId() throws SQLException, ClassNotFoundException {
        String currentBatchId = chickBatchModel.getCurrentBatchId();

        if (currentBatchId != null) {
            lblBatchId.setText(currentBatchId);
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }
}

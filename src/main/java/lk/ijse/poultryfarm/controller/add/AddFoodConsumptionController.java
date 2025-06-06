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
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.food.FoodInventoryPageController;
import lk.ijse.poultryfarm.controller.mail.ForgotPasswordController;
import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.FoodConsumptionModel;
import lk.ijse.poultryfarm.model.FoodModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class AddFoodConsumptionController implements Initializable {
    public JFXComboBox<String> lblBatchId;
    public Label lblConsumptionId;
    public DatePicker inputDate;
    public Label lblFoodId;
    public TextField inputConsumption;
    public JFXButton btnSave;

    private final ChickBatchModel chickBatchModel = new ChickBatchModel();
    private final FoodConsumptionModel foodConsumptionModel = new FoodConsumptionModel();
    private final FoodModel foodModel = new FoodModel();

    private final String patternConsumption = "^[0-9]+(\\.[0-9]{1,2})?$";

    public void saveFoodConsumptionOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getValue();
        String consumptionId = lblConsumptionId.getText();
        String date = inputDate.getValue().toString();
        String foodId = lblFoodId.getText();
        String consumption = inputConsumption.getText();

        double foodRemain = Double.parseDouble(foodModel.foodInventory(foodId));
        double foodConsumed = Double.parseDouble(consumption);
        boolean canConsume = foodRemain >= foodConsumed;

        String selectedBatchArrivedDate = chickBatchModel.searchChickBatch(batchId).getFirst().getDate();
        LocalDate givenDate = LocalDate.parse(selectedBatchArrivedDate);
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(givenDate, today);

        if(daysBetween < 10){
            if (foodId.equals("F002") || foodId.equals("F003")) {
                new Alert(Alert.AlertType.ERROR, "This Chick Batch is too young for this food.").show();
                return;
            }

        } else if (daysBetween < 20) {
                if (foodId.equals("F001") || foodId.equals("F003")) {
                    new Alert(Alert.AlertType.ERROR, "This Chick Batch is not eligible for this food.").show();
                    return;
                }

        } else if (daysBetween <= 30) {
                    if (foodId.equals("F001") || foodId.equals("F002")){
                        new Alert(Alert.AlertType.ERROR,"This Chick Batch is too old for this food.").show();
                        return;
                }
        } else {
            new Alert(Alert.AlertType.ERROR,"This Chick Batch's Food Consumption Period is over.").show();
            return;
        }

        if(canConsume){
            FoodConsumptionDto foodConsumptionDto = new FoodConsumptionDto(
                    batchId,
                    consumptionId,
                    date,
                    foodId,
                    Double.parseDouble(consumption)
            );

            boolean isSaved = foodConsumptionModel.saveFoodConsumption(foodConsumptionDto);

            if (isSaved) {
                double fRemain = Double.parseDouble(foodModel.foodInventory(foodId));

                if (fRemain < 200) {
                    String subject = "Food Shortage in the Inventory.";
                    String message = "Food '" + foodModel.getFoodName(foodId) + "' is Low in the Inventory. Please Order them Immediately.";
                    ForgotPasswordController.sendMail(subject, message);
                }

                new Alert(Alert.AlertType.INFORMATION,"Food Consumption Saved").show();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

            } else {
                new Alert(Alert.AlertType.ERROR,"Food Consumption Saving Failed").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"Not Enough Food In Inventory").show();
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
            inputConsumption.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternConsumption)) {
                    inputConsumption.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(false);

                } else if (newVal.isEmpty()) {
                    inputConsumption.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(true);

                } else {
                    inputConsumption.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    btnSave.setDisable(true);
                }
            });

            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();
            loadBatchId();
            lblFoodId.setText(FoodInventoryPageController.globalFoodId);
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
            lblBatchId.setValue(currentBatchId);
            lblBatchId.setItems(chickBatchModel.getAllBatchIds());
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }
}

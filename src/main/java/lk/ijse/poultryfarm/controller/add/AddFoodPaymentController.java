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
import lk.ijse.poultryfarm.dto.FoodPaymentDto;
import lk.ijse.poultryfarm.model.FoodModel;
import lk.ijse.poultryfarm.model.FoodPaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddFoodPaymentController implements Initializable {
    public Label lblPaymentId;
    public Label lblFoodId;
    public TextField inputQuantity;
    public TextField inputPaidAmount;
    public DatePicker inputDate;
    public JFXButton btnSave;

    private final String patternQuantity = "^[0-9]+(\\.[0-9]{1,2})?$";
    private final String patternPaidAmount = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final FoodPaymentModel foodPaymentModel = new FoodPaymentModel();

    public void saveFoodPaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String paymentId = lblPaymentId.getText();
        String foodId = lblFoodId.getText();
        String quantity = inputQuantity.getText();
        String paidAmount = inputPaidAmount.getText();
        String date = inputDate.getValue().toString();

        FoodPaymentDto foodPaymentDto = new FoodPaymentDto(paymentId,foodId,Double.parseDouble(quantity),Double.parseDouble(paidAmount),date);

        boolean isSaved = foodPaymentModel.saveFoodPayment(foodPaymentDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION,"Food Payment Saved").show();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR,"Food Payment Failed").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSave.setDisable(true);
        AtomicBoolean validQuantity = new AtomicBoolean(false);
        AtomicBoolean validPaidAmount = new AtomicBoolean(false);

        try {
            inputQuantity.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternQuantity)) {
                    inputQuantity.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validQuantity.set(true);

                    if(validPaidAmount.get()){
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputQuantity.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validQuantity.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputQuantity.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validQuantity.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputPaidAmount.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternPaidAmount)) {
                    inputPaidAmount.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validPaidAmount.set(true);

                    if(validQuantity.get()){
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputPaidAmount.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validPaidAmount.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputPaidAmount.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validPaidAmount.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();
            lblFoodId.setText(FoodInventoryPageController.globalFoodId);
            ButtonScale.buttonScaling(btnSave);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Food Payment Failed").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = foodPaymentModel.getNextFoodPaymentId();
        lblPaymentId.setText(nextId);
    }
}

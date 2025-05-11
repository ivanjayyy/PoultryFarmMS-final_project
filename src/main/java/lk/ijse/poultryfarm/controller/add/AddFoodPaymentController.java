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

public class AddFoodPaymentController implements Initializable {
    public Label lblPaymentId;
    public Label lblFoodId;
    public TextField inputQuantity;
    public TextField inputPaidAmount;
    public DatePicker inputDate;
    public JFXButton btnSave;

    private final FoodPaymentModel foodPaymentModel = new FoodPaymentModel();
    private final FoodModel foodModel = new FoodModel();

    public void saveFoodPaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String paymentId = lblPaymentId.getText();
        String foodId = lblFoodId.getText();
        String quantity = inputQuantity.getText();
        String paidAmount = inputPaidAmount.getText();
        String date = inputDate.getValue().toString();

        FoodPaymentDto foodPaymentDto = new FoodPaymentDto(paymentId,foodId,Double.parseDouble(quantity),Double.parseDouble(paidAmount),date);

        boolean isSaved = foodPaymentModel.saveFoodPayment(foodPaymentDto);

        if (isSaved) {
            boolean isChanged = foodModel.updateAfterFoodOrder(foodPaymentDto);
            if(!isChanged){
                new Alert(Alert.AlertType.ERROR,"Food Order Failed").show();
            }

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
        try {
            inputDate.setValue(java.time.LocalDate.now());
            loadNextId();
            lblFoodId.setText(FoodInventoryPageController.globalFoodId);
            ButtonScale.textFieldScaling(inputQuantity);
            ButtonScale.textFieldScaling(inputPaidAmount);
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

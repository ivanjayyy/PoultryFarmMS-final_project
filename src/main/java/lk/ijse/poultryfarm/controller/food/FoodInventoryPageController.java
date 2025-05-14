package lk.ijse.poultryfarm.controller.food;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.model.FoodModel;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodInventoryPageController implements Initializable {
    public Label lblBoosterFood;
    public Label lblStarterFood;
    public Label lblFinisherFood;
    public static String globalFoodId;

    private final FoodModel foodModel = new FoodModel();
    public JFXButton btnBoosterConsume;
    public JFXButton btnBoosterOrder;
    public JFXButton btnStarterConsume;
    public JFXButton btnStarterOrder;
    public JFXButton btnFinisherConsume;
    public JFXButton btnFinisherOrder;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnBoosterConsume);
        ButtonScale.buttonScaling(btnBoosterOrder);
        ButtonScale.buttonScaling(btnStarterConsume);
        ButtonScale.buttonScaling(btnStarterOrder);
        ButtonScale.buttonScaling(btnFinisherConsume);
        ButtonScale.buttonScaling(btnFinisherOrder);

        resetPage();
    }

    private void openFoodConsumptionWindow() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddFoodConsumption.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add food consumption window").show();
        }
    }

    private void openFoodPaymentWindow() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddFoodPayment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add food payment window").show();
        }
    }

    private void resetPage() {
        try {
            String boosterRemain = foodModel.foodInventory("F001");
            String starterRemain = foodModel.foodInventory("F002");
            String finisherRemain = foodModel.foodInventory("F003");

            double bRemain = Double.parseDouble(boosterRemain);
            double sRemain = Double.parseDouble(starterRemain);
            double fRemain = Double.parseDouble(finisherRemain);
            if (fRemain < 200 || sRemain < 200 || bRemain < 200) {
                new Alert(Alert.AlertType.WARNING, "Warning: Chicken food is low in stock.").show();
            }

            lblBoosterFood.setText(boosterRemain);
            lblStarterFood.setText(starterRemain);
            lblFinisherFood.setText(finisherRemain);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food inventory").show();
        }
    }

    public void consumeBoosterOnAction(ActionEvent actionEvent) {
        globalFoodId = "F001";
        openFoodConsumptionWindow();
    }

    public void orderBoosterOnAction(ActionEvent actionEvent) {
        globalFoodId = "F001";
        openFoodPaymentWindow();
    }

    public void consumeStarterOnAction(ActionEvent actionEvent) {
        globalFoodId = "F002";
        openFoodConsumptionWindow();
    }

    public void orderStarterOnAction(ActionEvent actionEvent) {
        globalFoodId = "F002";
        openFoodPaymentWindow();
    }

    public void consumeFinisherOnAction(ActionEvent actionEvent) {
        globalFoodId = "F003";
        openFoodConsumptionWindow();
    }

    public void orderFinisherOnAction(ActionEvent actionEvent) {
        globalFoodId = "F003";
        openFoodPaymentWindow();
    }
}

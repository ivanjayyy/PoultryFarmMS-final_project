package lk.ijse.poultryfarm.controller.food;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.poultryfarm.model.FoodModel;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodInventoryPageController implements Initializable {
    public Label lblBoosterFood;
    public Label lblStarterFood;
    public Label lblFinisherFood;

    private final FoodModel foodModel = new FoodModel();

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String boosterRemain = foodModel.foodInventory("F001");
            String starterRemain = foodModel.foodInventory("F002");
            String finisherRemain = foodModel.foodInventory("F003");

            lblBoosterFood.setText(boosterRemain);
            lblStarterFood.setText(starterRemain);
            lblFinisherFood.setText(finisherRemain);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food inventory").show();
        }
    }
}

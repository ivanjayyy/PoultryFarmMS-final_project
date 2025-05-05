package lk.ijse.poultryfarm.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.poultryfarm.controller.ButtonScale;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FoodDashboardController implements Initializable {

    public AnchorPane ancFoodWindow;
    public JFXButton btnFoodInventory;
    public JFXButton btnFoodConsumption;
    public JFXButton btnFoodPayment;

    public void navigateTo(String path) {
        try {
            ancFoodWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancFoodWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancFoodWindow.heightProperty());

            ancFoodWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/food/FoodInventoryPage.fxml");
        ButtonScale.buttonScaling(btnFoodInventory);
        ButtonScale.buttonScaling(btnFoodConsumption);
        ButtonScale.buttonScaling(btnFoodPayment);
    }

    public void goFoodInventoryOnAction(ActionEvent actionEvent) {
        navigateTo("/view/food/FoodInventoryPage.fxml");
    }

    public void goFoodConsumptionOnAction(ActionEvent actionEvent) {
        navigateTo("/view/food/FoodConsumptionPage.fxml");
    }

    public void goFoodPaymentOnAction(ActionEvent actionEvent) {
        navigateTo("/view/food/FoodPaymentPage.fxml");
    }
}

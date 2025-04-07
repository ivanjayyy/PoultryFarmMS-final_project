package lk.ijse.poultryfarm.controller.app.food;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FoodDashboardController implements Initializable {

    public AnchorPane ancFoodWindow;

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
        navigateTo("/view/app/food/FoodInventoryPage.fxml");
    }

    public void goFoodConsumptionPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/food/FoodConsumptionPage.fxml");
    }

    public void goFoodInventoryPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/food/FoodInventoryPage.fxml");
    }
}

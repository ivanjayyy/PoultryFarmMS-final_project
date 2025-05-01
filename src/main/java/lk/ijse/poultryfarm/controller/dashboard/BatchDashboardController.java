package lk.ijse.poultryfarm.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BatchDashboardController implements Initializable {

    public AnchorPane ancBatchWindow;

    public void goBatchSalePageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/batch/BatchSalePage.fxml");
    }

    public void goBatchDetailsPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/batch/BatchDetailsPage.fxml");
    }

    public void goBatchStatusPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/batch/BatchStatusPage.fxml");
    }

    public void navigateTo(String path) {
        try {
            ancBatchWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancBatchWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancBatchWindow.heightProperty());

            ancBatchWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/batch/BatchDetailsPage.fxml");
    }

}

package lk.ijse.poultryfarm.controller.app.other;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OtherDashboardController implements Initializable {

    public AnchorPane ancOtherWindow;

    public void navigateTo(String path) {
        try {
            ancOtherWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOtherWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOtherWindow.heightProperty());

            ancOtherWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/app/other/BillManagementPage.fxml");
    }

    public void goWasteManagementPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/other/WasteManagementPage.fxml");
    }

    public void goBillManagementPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/app/other/BillManagementPage.fxml");
    }
}

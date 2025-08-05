package lk.ijse.poultryfarm.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.poultryfarm.controller.ButtonScale;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.time.LocalTime;

public class AppDashboardController implements Initializable {
    
    public AnchorPane ancAppWindow;
    public JFXButton btnBatch;
    public JFXButton btnFood;
    public JFXButton btnBill;
    public JFXButton btnWaste;
    public JFXButton btnUser;
    public JFXButton btnEmployee;
    public JFXButton btnTemperature;
    public Label lblTime;
    public Label lblDate;
    public ImageView imagePowerButton;

    public void currentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.format(formatter));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void openEmployeeWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/dashboard/EmployeeDashboard.fxml");
    }

    public void openFoodWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/dashboard/FoodDashboard.fxml");
    }

    public void goOwnerAccountOnAction(ActionEvent actionEvent) {
        navigateTo("/view/owner/OwnerAccount.fxml");
    }

    public void openBillWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/BillManagementPage.fxml");
    }

    public void openBatchWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/dashboard/BatchDashboard.fxml");
    }

    public void openWasteWindowOnAction(ActionEvent actionEvent) {
        navigateTo("/view/WasteManagementPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTime();
        lblDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        navigateTo("/view/dashboard/BatchDashboard.fxml");
        ButtonScale.buttonScaling(btnBatch);
        ButtonScale.buttonScaling(btnFood);
        ButtonScale.buttonScaling(btnBill);
        ButtonScale.buttonScaling(btnWaste);
        ButtonScale.buttonScaling(btnTemperature);
        ButtonScale.buttonScaling(btnUser);
        ButtonScale.buttonScaling(btnEmployee);
        ButtonScale.imageScaling(imagePowerButton);
    }

    public void navigateTo(String path) {
        try {
            ancAppWindow.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancAppWindow.widthProperty());
            anchorPane.prefHeightProperty().bind(ancAppWindow.heightProperty());

            ancAppWindow.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    public void checkTemperatureOnAction(ActionEvent actionEvent) {
        navigateTo("/view/temperature/CheckTemperature.fxml");
    }

    public void closeProjectOnClicked(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Exit?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            System.exit(0);
        }
    }
}

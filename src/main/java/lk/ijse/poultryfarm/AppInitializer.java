package lk.ijse.poultryfarm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login/LoginDashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Poultry Farm Management System");
        stage.show();
    }

}

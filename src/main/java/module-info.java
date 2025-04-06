module lk.ijse.poultryfarm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens lk.ijse.poultryfarm.controller.app.other to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.app.order to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.app.batch to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.app.food to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.app.employee to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.login to javafx.fxml;
    exports lk.ijse.poultryfarm;
}
module lk.ijse.poultryfarm {
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;
    requires static lombok;

    exports lk.ijse.poultryfarm;
    opens lk.ijse.poultryfarm.controller to javafx.fxml;
}
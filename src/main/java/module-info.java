module lk.ijse.poultryfarm {
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires javafx.controls;
    requires static lombok;
    requires java.desktop;
    requires java.mail;
    requires net.sf.jasperreports.core;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires javafx.swing;
    requires webcam.capture;
    requires jcommander;

    exports lk.ijse.poultryfarm;
    opens lk.ijse.poultryfarm.controller to javafx.fxml;
    opens lk.ijse.poultryfarm.dto.tm to javafx.base;
    opens lk.ijse.poultryfarm.controller.add to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.dashboard to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.batch to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.owner to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.employee to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.food to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.mail to javafx.fxml;
    opens lk.ijse.poultryfarm.controller.temperature to javafx.fxml;
}
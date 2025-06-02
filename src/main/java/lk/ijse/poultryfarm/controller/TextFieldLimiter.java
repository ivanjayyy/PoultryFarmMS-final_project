package lk.ijse.poultryfarm.controller;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class TextFieldLimiter {
    public static void limitToOneDigit(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d?")) {
                return change;
            } else {
                return null;
            }
        }));
    }
}

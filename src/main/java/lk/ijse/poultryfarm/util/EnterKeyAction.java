package lk.ijse.poultryfarm.util;

import javafx.scene.control.TextField;

public class EnterKeyAction {
    public static void setEnterKeyMove(TextField current, TextField next) {
        current.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    if (next != null) {
                        next.requestFocus();
                    }
                    break;
            }
        });
    }
}

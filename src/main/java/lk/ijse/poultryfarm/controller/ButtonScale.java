package lk.ijse.poultryfarm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ButtonScale {
    public static void buttonScaling(JFXButton button) {

        ScaleTransition grow = new ScaleTransition(Duration.millis(100), button);
        grow.setToX(1.2);
        grow.setToY(1.2);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), button);
        shrink.setToX(1);
        shrink.setToY(1);

        button.setOnMouseEntered(e -> grow.playFromStart());
        button.setOnMouseExited(e -> shrink.playFromStart());
    }

    public static void textFieldScaling(TextField txt) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(100), txt);
        grow.setToX(1.2);
        grow.setToY(1.2);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), txt);
        shrink.setToX(1);
        shrink.setToY(1);

        txt.setOnMouseEntered(e -> grow.playFromStart());
        txt.setOnMouseExited(e -> shrink.playFromStart());
    }

    public static void imageScaling(ImageView imageView) {

        ScaleTransition grow = new ScaleTransition(Duration.millis(100), imageView);
        grow.setToX(1.2);
        grow.setToY(1.2);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(100), imageView);
        shrink.setToX(1);
        shrink.setToY(1);

        imageView.setOnMouseEntered(e -> grow.playFromStart());
        imageView.setOnMouseExited(e -> shrink.playFromStart());
    }
}

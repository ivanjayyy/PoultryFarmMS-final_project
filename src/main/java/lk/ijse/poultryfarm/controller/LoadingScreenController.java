package lk.ijse.poultryfarm.controller;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.image.WritableImage;
import javafx.util.Duration;

public class LoadingScreenController implements Initializable {
    public ImageView imageView;

    private static final int FRAME_WIDTH = 184;
    private static final int FRAME_HEIGHT = 184;
    private static final int COLUMNS = 2;
    private static final int TOTAL_FRAMES = 6;
    private static final int FRAME_DURATION_MS = 150;

    private int currentFrameIndex = 0;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image spriteSheet = new Image(getClass().getResource("/images/LoadingScreen.png.png").toExternalForm());
        imageView.setFitWidth(FRAME_WIDTH);
        imageView.setFitHeight(FRAME_HEIGHT);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(FRAME_DURATION_MS), e -> {
            int col = currentFrameIndex % COLUMNS;
            int row = currentFrameIndex / COLUMNS;
            WritableImage frame = new WritableImage(
                    spriteSheet.getPixelReader(),
                    col * FRAME_WIDTH, row * FRAME_HEIGHT,
                    FRAME_WIDTH, FRAME_HEIGHT
            );
            imageView.setImage(frame);
            currentFrameIndex = (currentFrameIndex + 1) % TOTAL_FRAMES;
        }));

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
}

package lk.ijse.poultryfarm.controller.mail;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.TextFieldLimiter;
import lk.ijse.poultryfarm.model.OwnerModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    public TextField one;
    public TextField two;
    public TextField three;
    public TextField four;
    public TextField five;
    public TextField six;
    public Label lblCountDown;
    public JFXButton btnResend;
    public JFXButton btnVerify;
    public AnchorPane ancForgotPassword;
    public ImageView imageView;

    private static final int FRAME_WIDTH = 184;
    private static final int FRAME_HEIGHT = 184;
    private static final int COLUMNS = 2;
    private static final int TOTAL_FRAMES = 6;
    private static final int FRAME_DURATION_MS = 150;

    private int currentFrameIndex = 0;

    private Timeline timeline;
    private int timeLeft;

    static int code;

    OwnerModel ownerModel = new OwnerModel();

    public void resendEmailOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Random random = new Random();
        code = 100000 + random.nextInt(900000);

        String username = ownerModel.ownerUsername();

        String subject = "Change Password Verification Code";
        String messageText = "Hello "+username+",\nYour verification code is: " + code;
        sendMail(subject,messageText);
        setTimer();
    }

    public void verifyCodeOnAction(ActionEvent actionEvent) {
        String inputCode = one.getText() + two.getText() + three.getText() + four.getText() + five.getText() + six.getText();
        if(inputCode.equals(String.valueOf(code))){
            try {
                ancForgotPassword.getChildren().clear();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/ChangePassword.fxml"));

                anchorPane.prefWidthProperty().bind(ancForgotPassword.widthProperty());
                anchorPane.prefHeightProperty().bind(ancForgotPassword.heightProperty());

                ancForgotPassword.getChildren().add(anchorPane);
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Page not found").show();
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect Verification Code").show();
        }
    }

    public static void sendMail(String subject, String messageText) {
        final String fromEmail = "poultryfarmms@gmail.com";
        final String appPassword = "httc oscc ghpb exji";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            OwnerModel ownerModel = new OwnerModel();
            String toEmail = ownerModel.getEmail();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION,"Email Sent Successfully").show();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random random = new Random();
        code = 100000 + random.nextInt(900000);

        ButtonScale.buttonScaling(btnResend);
        ButtonScale.buttonScaling(btnVerify);

        String username;
        try {
            username = ownerModel.ownerUsername();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String subject = "Change Password Verification Code";
        String messageText = "Hello "+username+",\nYour verification code is: " + code;
        sendMail(subject,messageText);
        setTimer();

        addImage();

        TextFieldLimiter.limitToOneDigit(one);
        TextFieldLimiter.limitToOneDigit(two);
        TextFieldLimiter.limitToOneDigit(three);
        TextFieldLimiter.limitToOneDigit(four);
        TextFieldLimiter.limitToOneDigit(five);
        TextFieldLimiter.limitToOneDigit(six);

        setAutoMove(one, two, null);
        setAutoMove(two, three, one);
        setAutoMove(three, four, two);
        setAutoMove(four, five, three);
        setAutoMove(five, six, four);
        setAutoMove(six, null, five);
    }

    private void setAutoMove(TextField current, TextField next, TextField prev) {
        current.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() == 1 && next != null) {
                next.requestFocus();
            }
        });

        current.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case BACK_SPACE:
                    if (current.getText().isEmpty() && prev != null) {
                        prev.requestFocus();
                    }
                    break;
            }
        });
    }

    private void addImage() {
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

    public void setTimer(){
        timeLeft = 60;
        lblCountDown.setText(String.valueOf(timeLeft));
        btnResend.setDisable(true);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeLeft--;
                    lblCountDown.setText(String.valueOf(timeLeft));
                    if(timeLeft == 0){
                        timeline.stop();
                        btnResend.setDisable(false);
                        lblCountDown.setText("");
                    }
                })
        );
        timeline.setCycleCount(timeLeft);
        timeline.play();
    }
}

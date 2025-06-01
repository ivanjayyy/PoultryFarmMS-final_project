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
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
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

    private Timeline timeline;
    private int timeLeft;

    static int code;

    public void resendEmailOnAction(ActionEvent actionEvent) {
        Random random = new Random();
        code = 100000 + random.nextInt(900000);

        String subject = "Forgot Password Verification Code";
        String messageText = "Your verification code is: " + code;
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
        final String fromEmail = "ivanjayasooriya03@gmail.com";
        final String appPassword = "cekz mqnn qsgw osdt";

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

        String subject = "Forgot Password Verification Code";
        String messageText = "Your verification code is: " + code;
        sendMail(subject,messageText);
        setTimer();
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

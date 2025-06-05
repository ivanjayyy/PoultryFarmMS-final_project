package lk.ijse.poultryfarm.controller.batch;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lk.ijse.poultryfarm.controller.ButtonScale;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QRCodeController implements Initializable {
    public ImageView qrImageView;
    public JFXButton btnScan;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnScan);

        String data = "Batch ID       : "+BatchDetailsPageController.selectedBatchId +
                "\nArrived Date : "+BatchDetailsPageController.selectedBatchDate +
                "\nTotal Chicks : "+BatchDetailsPageController.selectedBatchTotalChicks;
        try {
            int width = 250, height = 250;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            Image qrImage = SwingFXUtils.toFXImage(bufferedImage, null);
            qrImageView.setImage(qrImage);

            String imageName = "src/main/resources/images/batch_qr/" + BatchDetailsPageController.selectedBatchId + "qrCode.png";

            Path outputPath = Paths.get(imageName);
            MatrixToImageWriter.writeToPath(matrix, "PNG", outputPath);
            System.out.println("QR Code saved to: " + outputPath.toAbsolutePath());

        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void scanQRCodeOnAction(ActionEvent actionEvent) {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        Result result = null;

        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            result = new MultiFormatReader().decode(bitmap);
            System.out.println("Decoded QR: " + result.getText());
            String output = "Decoded QR:\n" + result.getText();
            new Alert(Alert.AlertType.INFORMATION, output).show();
        } catch (NotFoundException e) {
            System.out.println("QR code not found. Try again.");
        }
    }
}

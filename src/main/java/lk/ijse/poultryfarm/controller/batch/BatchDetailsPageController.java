package lk.ijse.poultryfarm.controller.batch;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.database.DBConnection;
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.dto.tm.BatchDetailsTm;
import lk.ijse.poultryfarm.dao.custom.impl.ChickBatchDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.ChickStatusDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.SaleDAOImpl;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BatchDetailsPageController implements Initializable {
    public static String selectedBatchId;
    public static String selectedBatchDate;
    public static int selectedBatchTotalChicks;
    public static double selectedBatchPayment;
    public static int selectedBatchChicksLeft;

    public JFXButton btnSearch;
    public JFXButton btnAdd;

    public TextField inputSearch;
    public Label lblTotalDays;
    public Label lblChicksLeft;
    public Label lblChicksDead;

    public TableView<BatchDetailsTm> tblBatchDetails;
    public TableColumn<BatchDetailsTm,String> colBatchId;
    public TableColumn<BatchDetailsTm,Integer> colTotalChicks;
    public TableColumn<BatchDetailsTm,Double> colPaymentMade;
    public TableColumn<BatchDetailsTm,String> colArrivedDate;

    private final ChickBatchDAOImpl chickBatchModel = new ChickBatchDAOImpl();
    public JFXButton btnSale;
    public JFXButton btnStatus;

    private final SaleDAOImpl saleModel = new SaleDAOImpl();
    private final ChickStatusDAOImpl chickStatusModel = new ChickStatusDAOImpl();
    public JFXButton btnReset;
    public JFXComboBox<String> searchBatchId;
    public Label lblBatchSold;
    public JFXButton btnReport;
    public JFXButton btnQR;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ButtonScale.buttonScaling(btnAdd);
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnSale);
        ButtonScale.buttonScaling(btnStatus);
        ButtonScale.buttonScaling(btnReset);
        ButtonScale.buttonScaling(btnReport);
        ButtonScale.buttonScaling(btnQR);

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colTotalChicks.setCellValueFactory(new PropertyValueFactory<>("chickTotal"));
        colPaymentMade.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colArrivedDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void resetPage() {
        try {
            btnSearch.setDisable(true);
            btnSale.setDisable(true);
            btnStatus.setDisable(true);
            btnAdd.setDisable(false);
            btnReport.setDisable(true);
            btnQR.setDisable(true);

            searchBatchId.getItems().clear();
            searchBatchId.setItems(chickBatchModel.getAllBatchIds());

            loadTableData();

            String currentBatchId = chickBatchModel.getCurrentBatchId();
            if(currentBatchId != null) {
                selectedBatchId = currentBatchId;
                selectedBatchTotalChicks = chickBatchModel.searchChickBatch(currentBatchId).getFirst().getChickTotal();
                loadBatchDetails();

                selectedBatchDate = chickBatchModel.searchChickBatch(currentBatchId).getFirst().getDate();
                LocalDate givenDate = LocalDate.parse(selectedBatchDate);
                LocalDate today = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(givenDate, today);
                lblTotalDays.setText(String.valueOf(daysBetween));
            }

            inputSearch.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    private void loadBatchDetails() throws SQLException, ClassNotFoundException {
        int sumOfChickDead = chickStatusModel.selectedBatchChickDeaths(selectedBatchId);
        int batchChicksLeft = (selectedBatchTotalChicks - sumOfChickDead);
        int totalChickSold = saleModel.selectedBatchTotalSold(selectedBatchId);

        selectedBatchChicksLeft = (batchChicksLeft - totalChickSold);
        boolean isSold = (batchChicksLeft == totalChickSold);

        if (isSold) {
            lblBatchSold.setText("YES");
        } else {
            lblBatchSold.setText("NO");
        }

        lblChicksDead.setText(String.valueOf(sumOfChickDead));
        lblChicksLeft.setText(String.valueOf(batchChicksLeft));
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ChickBatchDto> chickBatchDtos = chickBatchModel.getAllChickBatch();
        ObservableList<BatchDetailsTm> batchDetailsTms = FXCollections.observableArrayList();

        for (ChickBatchDto chickBatchDto : chickBatchDtos) {
            BatchDetailsTm batchDetailsTm = new BatchDetailsTm(
                    chickBatchDto.getBatchId(),
                    chickBatchDto.getChickTotal(),
                    chickBatchDto.getPayment(),
                    chickBatchDto.getDate()
            );
            batchDetailsTms.add(batchDetailsTm);
        }
        tblBatchDetails.setItems(batchDetailsTms);
    }

    public void searchBatchOnAction(ActionEvent actionEvent) {
        try {
            ArrayList<ChickBatchDto> chickBatchDtos = chickBatchModel.searchChickBatch(inputSearch.getText());
            ObservableList<BatchDetailsTm> batchDetailsTms = FXCollections.observableArrayList();

            for (ChickBatchDto chickBatchDto : chickBatchDtos) {
                BatchDetailsTm batchDetailsTm = new BatchDetailsTm(
                        chickBatchDto.getBatchId(),
                        chickBatchDto.getChickTotal(),
                        chickBatchDto.getPayment(),
                        chickBatchDto.getDate()
                );
                batchDetailsTms.add(batchDetailsTm);
            }
            tblBatchDetails.setItems(batchDetailsTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void addBatchOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddChickBatch.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add batch window").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        BatchDetailsTm selectedItem = tblBatchDetails.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedBatchId = selectedItem.getBatchId();
            selectedBatchTotalChicks = selectedItem.getChickTotal();
            selectedBatchPayment = selectedItem.getPayment();
            selectedBatchDate = selectedItem.getDate();

            LocalDate givenDate = LocalDate.parse(selectedBatchDate);
            LocalDate today = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(givenDate, today);
            lblTotalDays.setText(String.valueOf(daysBetween));

            loadBatchDetails();
            btnAdd.setDisable(true);
            btnSale.setDisable(true);
            btnStatus.setDisable(true);
            btnReport.setDisable(true);
            btnQR.setDisable(false);

            if(25 <= daysBetween && daysBetween <= 30 && lblBatchSold.getText().equals("NO")) {
                btnSale.setDisable(false);
            }

            int checkTodayStatusCount = chickStatusModel.checkStatus(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), selectedBatchId);
            if(lblBatchSold.getText().equals("NO") && daysBetween <= 30 && checkTodayStatusCount == 0) {
                btnStatus.setDisable(false);
            }

            if(25 <= daysBetween && daysBetween <= 30 && lblBatchSold.getText().equals("YES")){
                btnReport.setDisable(false);
            }
        }
    }

    public void addBatchSaleOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddSale.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add batch sale window").show();
        }
    }

    public void addChickStatusOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddChickStatus.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add chick status window").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchBatchIdOnAction(ActionEvent actionEvent) {
        btnSearch.setDisable(false);
        String batchId = searchBatchId.getSelectionModel().getSelectedItem();
        inputSearch.setText(batchId);
    }

    public void createBatchReportOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            JasperReport report = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/BatchRevenueReport.jrxml")
            );

            String batchId = selectedBatchId;

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Batch_ID", batchId);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    report,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Error in creating batch report").show();
            e.printStackTrace();
        }
    }

    public void createQRCodeOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/batch/QRCode.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening QR Code").show();
        }
    }
}

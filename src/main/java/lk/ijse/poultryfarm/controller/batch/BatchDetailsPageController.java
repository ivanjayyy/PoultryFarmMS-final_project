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
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.ChickStatusModel;
import lk.ijse.poultryfarm.model.SaleModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class BatchDetailsPageController implements Initializable {
    public static boolean updateChickBatch;
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

    private final ChickBatchModel chickBatchModel = new ChickBatchModel();
    public JFXButton btnSale;
    public JFXButton btnStatus;
    public JFXButton btnUpdate;

    private final SaleModel saleModel = new SaleModel();
    private final ChickStatusModel chickStatusModel = new ChickStatusModel();
    public JFXButton btnReset;
    public JFXComboBox<String> searchBatchId;
    public Label lblBatchSold;
    public JFXButton btnReport;

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
        ButtonScale.buttonScaling(btnUpdate);
        ButtonScale.buttonScaling(btnReset);
        ButtonScale.buttonScaling(btnReport);

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
            btnUpdate.setDisable(true);
            btnSale.setDisable(true);
            btnStatus.setDisable(true);
            btnAdd.setDisable(false);
            btnReport.setDisable(true);

            searchBatchId.getItems().clear();
            searchBatchId.setItems(chickBatchModel.getAllBatchIds());

            loadTableData();
            lblTotalDays.setText("");

            String currentBatchId = chickBatchModel.getCurrentBatchId();
            selectedBatchId = currentBatchId;
            selectedBatchTotalChicks = chickBatchModel.searchChickBatch(currentBatchId).getFirst().getChickTotal();
            loadBatchDetails();

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
            btnUpdate.setDisable(true);
            btnAdd.setDisable(true);
            btnSale.setDisable(true);
            btnStatus.setDisable(true);
            btnReport.setDisable(true);

            if(25 <= daysBetween && daysBetween <= 30 && lblBatchSold.getText().equals("NO")) {
                btnSale.setDisable(false);
            }

            if(lblBatchSold.getText().equals("NO") && daysBetween <= 30) {
                btnStatus.setDisable(false);
                btnUpdate.setDisable(false);
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

    public void updateBatchDetailsOnAction(ActionEvent actionEvent) {
        updateChickBatch = true;
        try{
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
        updateChickBatch = false;
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
}

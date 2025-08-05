package lk.ijse.poultryfarm.controller.add;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.controller.batch.BatchDetailsPageController;
import lk.ijse.poultryfarm.controller.batch.BatchSalePageController;
import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.dao.custom.impl.ChickBatchDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.ChickStatusDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.SaleDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddSaleController implements Initializable {
    public Label lblBatchId;
    public Label lblSaleId;
    public TextField inputTotalSale;
    public DatePicker inputSoldDate;
    public TextField inputChicksSold;
    public JFXButton btnSave;

    private final String patternChicksSold = "^[0-9]+$";
    private final String patternTotalSale = "^(0|[1-9][0-9]*)?(\\.[0-9]{1,2})?$";

    private final SaleDAOImpl saleModel = new SaleDAOImpl();

    public int originalSoldChicks;

    public void saveBatchSaleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String batchId = lblBatchId.getText();
        String saleId = lblSaleId.getText();
        String totalSale = inputTotalSale.getText();
        String date = inputSoldDate.getValue().toString();
        String chicksSold = inputChicksSold.getText();

        SaleDto saleDto = new SaleDto(batchId,saleId,Double.parseDouble(totalSale),date,Integer.parseInt(chicksSold));

        ChickStatusDAOImpl chickStatusModel = new ChickStatusDAOImpl();
        ChickBatchDAOImpl chickBatchModel = new ChickBatchDAOImpl();

        int sumOfChickDead = chickStatusModel.selectedBatchChickDeaths(batchId);
        int chicksSoldToday = Integer.parseInt(chicksSold);
        int batchChickTotal = chickBatchModel.getChickTotal(batchId);

        SaleDAOImpl saleModel = new SaleDAOImpl();
        int totalSold = saleModel.selectedBatchTotalSold(batchId);

        int allSoldChicks = chicksSoldToday + totalSold;
        int allSoldChicksForUpdate = chicksSoldToday + totalSold - originalSoldChicks;
        int batchChicksLeft = batchChickTotal - sumOfChickDead;

        boolean isValidForUpdate = (allSoldChicksForUpdate <= batchChicksLeft);
        boolean isValid = (allSoldChicks <= batchChicksLeft);

        if(BatchSalePageController.updateSale){
            if(isValidForUpdate){
                boolean isUpdated = saleModel.updateSale(saleDto);

                if(isUpdated){
                    new Alert(Alert.AlertType.INFORMATION,"Sale Updated Successfully").show();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.close();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Sale Update Failed").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Not Enough Chicks For Sale.").show();
            }

        } else {
            if(isValid){
                boolean isSaved = saleModel.saveSale(saleDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION,"Sale Saved Successfully").show();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.close();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Sale Save Failed").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Not Enough Chicks For Sale.").show();
            }
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSave.setDisable(true);
        AtomicBoolean validChickSold = new AtomicBoolean(false);
        AtomicBoolean validTotalSale = new AtomicBoolean(false);

        try {
            inputChicksSold.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternChicksSold)) {
                    inputChicksSold.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validChickSold.set(true);

                    if(validTotalSale.get()){
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputChicksSold.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validChickSold.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputChicksSold.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validChickSold.set(false);
                    btnSave.setDisable(true);
                }
            });

            inputTotalSale.textProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal.matches(patternTotalSale)) {
                    inputTotalSale.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validTotalSale.set(true);

                    if(validChickSold.get()){
                        btnSave.setDisable(false);
                    }

                } else if (newVal.isEmpty()) {
                    inputTotalSale.setStyle("-fx-text-inner-color: black; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validTotalSale.set(false);
                    btnSave.setDisable(true);

                } else {
                    inputTotalSale.setStyle("-fx-text-inner-color: red; -fx-background-color: white; -fx-border-width: 0 0 1px 0; -fx-border-color: gray;");
                    validTotalSale.set(false);
                    btnSave.setDisable(true);
                }
            });

            btnSave.setText("SAVE");
            inputSoldDate.setValue(java.time.LocalDate.now());
            inputChicksSold.setText(String.valueOf(BatchDetailsPageController.selectedBatchChicksLeft));
            loadNextId();
            loadBatchId();
            ButtonScale.buttonScaling(btnSave);

            if(BatchSalePageController.updateSale){
                originalSoldChicks = Integer.parseInt(BatchSalePageController.selectedBatchChicksSold);
                lblBatchId.setText(BatchSalePageController.selectedBatchId);
                lblSaleId.setText(BatchSalePageController.selectedSaleId);
                inputSoldDate.setValue(LocalDate.parse(BatchSalePageController.selectedBatchDate));
                inputTotalSale.setText(String.valueOf(BatchSalePageController.selectedBatchTotalSale));
                inputChicksSold.setText(String.valueOf(BatchSalePageController.selectedBatchChicksSold));

                btnSave.setText("UPDATE");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    private void loadBatchId()  {
        String batchId = BatchDetailsPageController.selectedBatchId;

        if (batchId != null) {
            lblBatchId.setText(batchId);
        }else {
            new Alert(Alert.AlertType.WARNING,"No Chicken Batch Exists. Please add a new chicken batch first.").show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = saleModel.getNextSaleId();
        lblSaleId.setText(nextId);
    }
}

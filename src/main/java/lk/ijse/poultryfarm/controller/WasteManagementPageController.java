package lk.ijse.poultryfarm.controller;

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
import lk.ijse.poultryfarm.dto.WasteManagementDto;
import lk.ijse.poultryfarm.dto.tm.WasteManagementTm;
import lk.ijse.poultryfarm.model.WasteManagementModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class WasteManagementPageController implements Initializable {
    public JFXButton btnAdd;
    public TableView<WasteManagementTm> tblWaste;
    public TableColumn<WasteManagementTm,String> colBatchId;
    public TableColumn<WasteManagementTm,String> colWasteId;
    public TableColumn<WasteManagementTm,Double> colTotalAmount;
    public TableColumn<WasteManagementTm,String> colSoldDate;

    private final WasteManagementModel wasteManagementModel = new WasteManagementModel();
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXButton btnReset;
    public JFXComboBox searchBatchId;

    public void addWasteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddWasteManagement.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add waste management window").show();
        }
    }

    private void resetPage() {
       try {
           btnDelete.setDisable(true);
           btnUpdate.setDisable(true);
           btnAdd.setDisable(false);
           loadTableData();
           inputSearch.clear();
        } catch (Exception e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
       }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<WasteManagementDto> wasteManagementDtos = wasteManagementModel.getAllWasteManagement();
        ObservableList<WasteManagementTm> wasteManagementTms = FXCollections.observableArrayList();
        for (WasteManagementDto wasteManagementDto : wasteManagementDtos) {
            WasteManagementTm wasteManagementTm = new WasteManagementTm(
                    wasteManagementDto.getBatchId(),
                    wasteManagementDto.getWasteId(),
                    wasteManagementDto.getTotalSale(),
                    wasteManagementDto.getDate()
            );
            wasteManagementTms.add(wasteManagementTm);
        }
        tblWaste.setItems(wasteManagementTms);
    }

    public static String selectedBatchId;
    public static String selectedWasteId;
    public static double selectedWasteAmount;
    public static String selectedWasteDate;
    public static boolean updateWaste;

    public void onClickTable(MouseEvent mouseEvent) {
        btnAdd.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);

        WasteManagementTm selectedItem = tblWaste.getSelectionModel().getSelectedItem();
        try {
            if (selectedItem != null) {
                selectedBatchId = selectedItem.getBatchId();
                selectedWasteId = selectedItem.getWasteId();
                selectedWasteAmount = selectedItem.getTotalSale();
                selectedWasteDate = selectedItem.getDate();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colWasteId.setCellValueFactory(new PropertyValueFactory<>("wasteId"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalSale"));
        colSoldDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        ButtonScale.buttonScaling(btnAdd);
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnDelete);
        ButtonScale.buttonScaling(btnUpdate);

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void searchWasteOnAction(ActionEvent actionEvent) {
        try{
            ArrayList<WasteManagementDto> wasteManagementDtos = wasteManagementModel.searchWasteManagement(inputSearch.getText());
            ObservableList<WasteManagementTm> wasteManagementTms = FXCollections.observableArrayList();
            for (WasteManagementDto wasteManagementDto : wasteManagementDtos) {
                WasteManagementTm wasteManagementTm = new WasteManagementTm(
                        wasteManagementDto.getBatchId(),
                        wasteManagementDto.getWasteId(),
                        wasteManagementDto.getTotalSale(),
                        wasteManagementDto.getDate()
                );
                wasteManagementTms.add(wasteManagementTm);
            }
            tblWaste.setItems(wasteManagementTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving batches").show();
        }
    }

    public void deleteWasteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = wasteManagementModel.deleteWasteManagement(selectedWasteId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION,"Waste deleted successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error in deleting waste").show();
                }
                resetPage();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Error in deleting batch sale").show();
            }
        }
    }

    public void updateWasteOnAction(ActionEvent actionEvent) {
        updateWaste = true;

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add/AddWasteManagement.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in opening add waste management window").show();
        }

        updateWaste = false;
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }
}

package lk.ijse.poultryfarm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
            loadTableData();
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

    public void onClickTable(MouseEvent mouseEvent) {
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
    }

    public void updateWasteOnAction(ActionEvent actionEvent) {
    }
}

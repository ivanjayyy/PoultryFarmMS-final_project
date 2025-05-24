package lk.ijse.poultryfarm.controller.food;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.poultryfarm.controller.ButtonScale;
import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.dto.tm.FoodConsumptionTm;
import lk.ijse.poultryfarm.model.ChickBatchModel;
import lk.ijse.poultryfarm.model.FoodConsumptionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FoodConsumptionPageController implements Initializable {
    public TableView<FoodConsumptionTm> tblFoodConsumption;
    public TableColumn<FoodConsumptionTm,String> colBatchId;
    public TableColumn<FoodConsumptionTm,String> colConsumptionId;
    public TableColumn<FoodConsumptionTm,String> colDate;
    public TableColumn<FoodConsumptionTm,String> colFoodId;
    public TableColumn<FoodConsumptionTm,Double> colConsumption;

    private final FoodConsumptionModel foodConsumptionModel = new FoodConsumptionModel();
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnReset;
    public JFXComboBox<String> searchBatchId;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnReset);

        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colConsumptionId.setCellValueFactory(new PropertyValueFactory<>("consumptionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colConsumption.setCellValueFactory(new PropertyValueFactory<>("consumption"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food consumption").show();
        }
    }

    private void resetPage() {
        try {
            loadTableDate();
            inputSearch.clear();

            ChickBatchModel chickBatchModel = new ChickBatchModel();
            searchBatchId.getItems().clear();
            searchBatchId.setItems(chickBatchModel.getAllBatchIds());

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food consumption").show();
        }
    }

    private void loadTableDate() throws SQLException, ClassNotFoundException {
        ArrayList<FoodConsumptionDto> foodConsumptionDtos = foodConsumptionModel.getAllFoodConsumption();
        ObservableList<FoodConsumptionTm> foodConsumptionTms = FXCollections.observableArrayList();

        for (FoodConsumptionDto foodConsumptionDto : foodConsumptionDtos) {
            FoodConsumptionTm foodConsumptionTm = new FoodConsumptionTm(
                    foodConsumptionDto.getBatchId(),
                    foodConsumptionDto.getConsumptionId(),
                    foodConsumptionDto.getDate(),
                    foodConsumptionDto.getFoodId(),
                    foodConsumptionDto.getConsumption()
            );
            foodConsumptionTms.add(foodConsumptionTm);
        }
        tblFoodConsumption.setItems(foodConsumptionTms);
    }

    public void searchFoodConsumptionOnAction(ActionEvent actionEvent) {
        try{
            ArrayList<FoodConsumptionDto> foodConsumptionDtos = foodConsumptionModel.searchFoodConsumption(inputSearch.getText());
            ObservableList<FoodConsumptionTm> foodConsumptionTms = FXCollections.observableArrayList();

            for (FoodConsumptionDto foodConsumptionDto : foodConsumptionDtos) {
                FoodConsumptionTm foodConsumptionTm = new FoodConsumptionTm(
                        foodConsumptionDto.getBatchId(),
                        foodConsumptionDto.getConsumptionId(),
                        foodConsumptionDto.getDate(),
                        foodConsumptionDto.getFoodId(),
                        foodConsumptionDto.getConsumption()
                );
                foodConsumptionTms.add(foodConsumptionTm);
            }
            tblFoodConsumption.setItems(foodConsumptionTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food consumption").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchBatchIdOnAction(ActionEvent actionEvent) {
        String batchId = searchBatchId.getSelectionModel().getSelectedItem();
        inputSearch.setText(batchId);
    }
}

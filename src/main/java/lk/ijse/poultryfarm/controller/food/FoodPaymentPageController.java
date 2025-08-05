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
import lk.ijse.poultryfarm.dto.FoodPaymentDto;
import lk.ijse.poultryfarm.dto.tm.FoodPaymentTm;
import lk.ijse.poultryfarm.dao.custom.impl.FoodDAOImpl;
import lk.ijse.poultryfarm.dao.custom.impl.FoodPaymentDAOImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FoodPaymentPageController implements Initializable {
    public TableView<FoodPaymentTm> tblFoodPayment;
    public TableColumn<FoodPaymentTm,String> colPaymentId;
    public TableColumn<FoodPaymentTm,String> colFoodId;
    public TableColumn<FoodPaymentTm,Double> colQuantity;
    public TableColumn<FoodPaymentTm,Double> colAmount;
    public TableColumn<FoodPaymentTm,String> colDate;

    private final FoodPaymentDAOImpl foodPaymentModel = new FoodPaymentDAOImpl();
    public TextField inputSearch;
    public JFXButton btnSearch;
    public JFXButton btnReset;
    public JFXComboBox<String> searchFoodName;

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonScale.buttonScaling(btnSearch);
        ButtonScale.buttonScaling(btnReset);

        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("foodPaymentId"));
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("foodId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("payAmount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food payment").show();
        }
    }

    private void resetPage() {
        try {
            loadTableData();
            inputSearch.clear();
            btnSearch.setDisable(true);

            searchFoodName.getItems().clear();
            searchFoodName.getItems().addAll("Booster","Starter","Finisher");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food payment").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<FoodPaymentDto> foodPaymentDtos = foodPaymentModel.getAllFoodPayment();
        ObservableList<FoodPaymentTm> foodPaymentTms = FXCollections.observableArrayList();
        for (FoodPaymentDto foodPaymentDto : foodPaymentDtos) {
            FoodPaymentTm foodPaymentTm = new FoodPaymentTm(
                    foodPaymentDto.getFoodPaymentId(),
                    foodPaymentDto.getFoodId(),
                    foodPaymentDto.getQuantity(),
                    foodPaymentDto.getPayAmount(),
                    foodPaymentDto.getDate()
            );
            foodPaymentTms.add(foodPaymentTm);
        }
        tblFoodPayment.setItems(foodPaymentTms);
    }

    public void searchFoodPaymentOnAction(ActionEvent actionEvent) {
        try{
            ArrayList<FoodPaymentDto> foodPaymentDtos = foodPaymentModel.searchFoodPayment(inputSearch.getText());
            ObservableList<FoodPaymentTm> foodPaymentTms = FXCollections.observableArrayList();
            for (FoodPaymentDto foodPaymentDto : foodPaymentDtos) {
                FoodPaymentTm foodPaymentTm = new FoodPaymentTm(
                        foodPaymentDto.getFoodPaymentId(),
                        foodPaymentDto.getFoodId(),
                        foodPaymentDto.getQuantity(),
                        foodPaymentDto.getPayAmount(),
                        foodPaymentDto.getDate()
                );
                foodPaymentTms.add(foodPaymentTm);
            }
            tblFoodPayment.setItems(foodPaymentTms);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error in retrieving food payment").show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void searchFoodNameOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSearch.setDisable(false);
        String foodName = searchFoodName.getSelectionModel().getSelectedItem();

        FoodDAOImpl foodModel = new FoodDAOImpl();
        inputSearch.setText(foodModel.getFoodId(foodName));
    }
}

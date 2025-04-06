package lk.ijse.poultryfarm.dto;

public class FoodConsumptionDto {

    private int foodConsumptionId;
    private String date;
    private int batchId;
    private int foodId;

    public FoodConsumptionDto() {
    }

    public FoodConsumptionDto(int foodConsumptionId, String date, int batchId, int foodId) {
        this.foodConsumptionId = foodConsumptionId;
        this.date = date;
        this.batchId = batchId;
        this.foodId = foodId;
    }

    public int getFoodConsumptionId() {
        return foodConsumptionId;
    }

    public void setFoodConsumptionId(int foodConsumptionId) {
        this.foodConsumptionId = foodConsumptionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

}

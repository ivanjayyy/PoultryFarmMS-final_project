package lk.ijse.poultryfarm.dto;

public class FoodDto {

    private int foodId;
    private String name;
    private double quantity;

    public FoodDto(int foodId, String name, double quantity) {
        this.foodId = foodId;
        this.name = name;
        this.quantity = quantity;
    }

    public FoodDto() {
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}

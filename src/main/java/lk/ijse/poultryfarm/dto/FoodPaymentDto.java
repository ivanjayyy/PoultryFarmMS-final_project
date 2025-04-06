package lk.ijse.poultryfarm.dto;

public class FoodPaymentDto {

    private int paymentId;
    private String date;
    private double amount;
    private int foodId;

    public FoodPaymentDto(int paymentId, String date, double amount, int foodId) {
        this.paymentId = paymentId;
        this.date = date;
        this.amount = amount;
        this.foodId = foodId;
    }

    public FoodPaymentDto() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

}

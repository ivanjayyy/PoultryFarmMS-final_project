package lk.ijse.poultryfarm.dto;

public class OrdersDto {

    private int orderId;
    private String date;
    private double amount;
    private int batchId;

    public OrdersDto(int orderId, String date, double amount, int batchId) {
        this.orderId = orderId;
        this.date = date;
        this.amount = amount;
        this.batchId = batchId;
    }

    public OrdersDto() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

}

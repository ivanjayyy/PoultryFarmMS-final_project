package lk.ijse.poultryfarm.dto;

public class ChickBatchDto {

    private int batchId;
    private int chickTotal;
    private String date;
    private double payment;
    private int ownerId;

    public ChickBatchDto(int batchId, int chickTotal, String date, double payment, int ownerId) {
        this.batchId = batchId;
        this.chickTotal = chickTotal;
        this.date = date;
        this.payment = payment;
        this.ownerId = ownerId;
    }

    public ChickBatchDto() {
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getChickTotal() {
        return chickTotal;
    }

    public void setChickTotal(int chickTotal) {
        this.chickTotal = chickTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}

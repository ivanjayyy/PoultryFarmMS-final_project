package lk.ijse.poultryfarm.dto;

public class BillDto {

    private int billId;
    private String billVariant;
    private double amount;
    private boolean status;
    private String date;
    private int batchId;

    public BillDto(int billId, String billVariant, double amount, boolean status, String date, int batchId) {
        this.billId = billId;
        this.billVariant = billVariant;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.batchId = batchId;
    }

    public BillDto() {
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillVariant() {
        return billVariant;
    }

    public void setBillVariant(String billVariant) {
        this.billVariant = billVariant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

}

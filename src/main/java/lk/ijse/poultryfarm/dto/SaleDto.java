package lk.ijse.poultryfarm.dto;

public class SaleDto {

    private int saleId;
    private String date;
    private double totalSale;
    private int batchId;

    public SaleDto() {
    }

    public SaleDto(int saleId, String date, double totalSale, int batchId) {
        this.saleId = saleId;
        this.date = date;
        this.totalSale = totalSale;
        this.batchId = batchId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(double totalSale) {
        this.totalSale = totalSale;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

}

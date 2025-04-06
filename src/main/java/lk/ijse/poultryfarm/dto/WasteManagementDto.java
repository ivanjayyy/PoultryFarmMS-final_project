package lk.ijse.poultryfarm.dto;

public class WasteManagementDto {

    private int wasteId;
    private String date;
    private double totalSale;
    private int batchId;

    public WasteManagementDto(int wasteId, String date, double totalSale, int batchId) {
        this.wasteId = wasteId;
        this.date = date;
        this.totalSale = totalSale;
        this.batchId = batchId;
    }

    public WasteManagementDto() {
    }

    public int getWasteId() {
        return wasteId;
    }

    public void setWasteId(int wasteId) {
        this.wasteId = wasteId;
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

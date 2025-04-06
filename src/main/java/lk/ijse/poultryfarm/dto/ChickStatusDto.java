package lk.ijse.poultryfarm.dto;

public class ChickStatusDto {

    private int mortalityId;
    private int chicksDead;
    private String date;
    private int batchId;

    public ChickStatusDto() {
    }

    public ChickStatusDto(int mortalityId, int chicksDead, String date, int batchId) {
        this.mortalityId = mortalityId;
        this.chicksDead = chicksDead;
        this.date = date;
        this.batchId = batchId;
    }

    public int getMortalityId() {
        return mortalityId;
    }

    public void setMortalityId(int mortalityId) {
        this.mortalityId = mortalityId;
    }

    public int getChicksDead() {
        return chicksDead;
    }

    public void setChicksDead(int chicksDead) {
        this.chicksDead = chicksDead;
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

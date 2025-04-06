package lk.ijse.poultryfarm.dto;

public class SupplierDto {

    private int supplierId;
    private String name;
    private String contact;
    private int productId;

    public SupplierDto(int supplierId, String name, String contact, int productId) {
        this.supplierId = supplierId;
        this.name = name;
        this.contact = contact;
        this.productId = productId;
    }

    public SupplierDto() {
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}

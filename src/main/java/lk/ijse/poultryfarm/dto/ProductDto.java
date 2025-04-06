package lk.ijse.poultryfarm.dto;

public class ProductDto {

    private int productId;
    private String name;

    public ProductDto() {
    }

    public ProductDto(int productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package lk.ijse.poultryfarm.dto;

public class OrderDetailDto {

    private int orderDetailId;
    private int orderId;
    private int productId;

    public OrderDetailDto(int orderDetailId, int orderId, int productId) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
    }

    public OrderDetailDto() {
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

}

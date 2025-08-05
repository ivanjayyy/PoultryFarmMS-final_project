package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FoodPayment {

    private String foodPaymentId;
    private String foodId;
    private double quantity;
    private double payAmount;
    private String date;

}

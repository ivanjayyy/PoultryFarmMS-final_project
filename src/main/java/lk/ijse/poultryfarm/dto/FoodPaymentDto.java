package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FoodPaymentDto {

    private String foodPaymentId;
    private String foodName;
    private double quantity;
    private double pay_amount;
    private String date;

}

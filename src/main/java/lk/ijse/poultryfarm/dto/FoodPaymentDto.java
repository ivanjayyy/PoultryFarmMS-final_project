package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FoodPaymentDto {

    private String foodPaymentId;
    private String foodId;
    private double quantity;
    private double payAmount;
    private String date;

}

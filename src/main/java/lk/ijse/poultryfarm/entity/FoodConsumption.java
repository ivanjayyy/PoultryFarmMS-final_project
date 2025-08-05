package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class FoodConsumption {

    private String batchId;
    private String consumptionId;
    private String date;
    private String foodId;
    private double consumption;

}

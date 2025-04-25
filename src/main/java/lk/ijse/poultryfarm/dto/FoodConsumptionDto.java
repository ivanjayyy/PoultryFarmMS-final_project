package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class FoodConsumptionDto {

    private String batchId;
    private String consumptionId;
    private String date;
    private String foodId;
    private double consumption;

}

package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class FoodConsumptionDto {

    private String batchId;
    private String foodName;
    private double consumption;
    private String date;

}

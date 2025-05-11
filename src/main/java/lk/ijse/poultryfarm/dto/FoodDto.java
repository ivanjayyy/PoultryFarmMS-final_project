package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class FoodDto {

    private String foodId;
    private String foodName;
    private double quantityRemain;
}

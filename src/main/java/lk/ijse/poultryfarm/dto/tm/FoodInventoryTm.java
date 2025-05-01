package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class FoodInventoryTm {

        private String foodId;
        private String foodName;
        private double quantityRemain;
}

package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class FoodConsumptionTm {
        private String batchId;
        private String consumptionId;
        private String date;
        private String foodId;
        private double consumption;
}

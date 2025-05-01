package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FoodPaymentTm {

        private String foodPaymentId;
        private String foodId;
        private double quantity;
        private double payAmount;
        private String date;
}

package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class BatchSaleTm {

        private String batchId;
        private String saleId;
        private double totalSale;
        private String date;
        private int chicksSold;
}

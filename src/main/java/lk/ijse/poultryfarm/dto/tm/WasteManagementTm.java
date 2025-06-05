package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class WasteManagementTm {
        private String batchId;
        private String wasteId;
        private double totalSale;
        private String date;
}

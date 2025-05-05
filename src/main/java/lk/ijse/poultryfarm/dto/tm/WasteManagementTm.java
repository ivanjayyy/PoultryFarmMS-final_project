package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

import java.awt.*;

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

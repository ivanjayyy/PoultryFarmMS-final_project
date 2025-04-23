package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class WasteManagementDto {

    private String batchId;
    private String wasteId;
    private double totalSale;
    private String date;

}

package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class WasteManagement {

    private String batchId;
    private String wasteId;
    private double totalSale;
    private String date;

}

package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class BillManagementTm {
    private String batchId;
    private String billId;
    private String billVariant;
    private double amount;
    private String date;
}
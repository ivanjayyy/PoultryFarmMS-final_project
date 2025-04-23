package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class BillDto {

    private String batchId;
    private String billId;
    private String billVariant;
    private double amount;
    private String date;

}

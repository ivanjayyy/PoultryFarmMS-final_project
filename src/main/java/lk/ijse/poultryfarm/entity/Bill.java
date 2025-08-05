package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Bill {
    private String batchId;
    private String billId;
    private String billVariant;
    private double amount;
    private String date;
}

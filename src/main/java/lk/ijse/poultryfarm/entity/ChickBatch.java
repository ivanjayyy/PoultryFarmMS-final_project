package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ChickBatch {

    private String batchId;
    private int chickTotal;
    private double payment;
    private String date;

}

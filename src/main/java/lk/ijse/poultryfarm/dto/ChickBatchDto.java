package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ChickBatchDto {

    private String batchId;
    private int chickTotal;
    private double payment;
    private String date;
    private String username;

}

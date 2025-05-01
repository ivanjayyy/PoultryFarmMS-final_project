package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BatchDetailsTm {
    private String batchId;
    private int chickTotal;
    private double payment;
    private String date;
}

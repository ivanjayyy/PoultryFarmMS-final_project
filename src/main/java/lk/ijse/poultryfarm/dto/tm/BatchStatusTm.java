package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class BatchStatusTm {
        private String batchId;
        private String chickStatusId;
        private String date;
        private int chicksDead;
}

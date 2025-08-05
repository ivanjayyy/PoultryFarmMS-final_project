package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ChickStatus {

    private String batchId;
    private String chickStatusId;
    private String date;
    private int chicksDead;
}

package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ChickStatusDto {

    private String batchId;
    private String chickStatusId;
    private String date;
    private int chicksDead;
}

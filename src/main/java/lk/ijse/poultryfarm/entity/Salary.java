package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Salary {

    private String salaryId;
    private String employeeId;
    private double amount;
    private String date;

}

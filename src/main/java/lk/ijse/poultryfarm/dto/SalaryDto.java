package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SalaryDto {

    private String salaryId;
    private String employeeId;
    private double amount;
    private String date;

}

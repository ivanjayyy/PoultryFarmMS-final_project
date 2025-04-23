package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SalaryDto {

    private String employeeId;
    private String salaryId;
    private double amount;
    private String date;

}

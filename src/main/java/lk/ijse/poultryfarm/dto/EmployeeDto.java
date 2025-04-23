package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {

    private String employeeId;
    private String name;
    private boolean fullTime;
    private String contact;
    private double dailyWage;

}

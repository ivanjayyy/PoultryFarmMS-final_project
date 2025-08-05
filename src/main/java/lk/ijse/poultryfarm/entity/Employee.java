package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Employee {

    private String employeeId;
    private String name;
    private String fullTime;
    private String contact;
    private double dailyWage;

}

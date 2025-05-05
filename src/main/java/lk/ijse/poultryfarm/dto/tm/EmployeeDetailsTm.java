package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDetailsTm {
        private String employeeId;
        private String name;
        private String fullTime;
        private String contact;
        private double dailyWage;
}

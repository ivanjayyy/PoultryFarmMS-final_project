package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SalaryManagementTm {
        private String salaryId;
        private String employeeId;
        private double amount;
        private String date;
}

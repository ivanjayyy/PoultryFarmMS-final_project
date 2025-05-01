package lk.ijse.poultryfarm.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EmployeeAttendanceTm {

        private String batchId;
        private String attendanceId;
        private String date;
        private String employeeId;
        private boolean attendance;
}

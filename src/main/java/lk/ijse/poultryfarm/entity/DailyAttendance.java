package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DailyAttendance {

    private String batchId;
    private String attendanceId;
    private String date;
    private String employeeId;
    private boolean attendance;

}

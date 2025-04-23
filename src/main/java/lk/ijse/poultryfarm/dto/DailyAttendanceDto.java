package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DailyAttendanceDto {

    private String batchId;
    private String employeeId;
    private String attendanceId;
    private String date;
    private boolean attendance;

}

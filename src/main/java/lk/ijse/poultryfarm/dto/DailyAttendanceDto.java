package lk.ijse.poultryfarm.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class DailyAttendanceDto {

    private String batchId;
    private String attendanceId;
    private String date;
    private String employeeId;
    private boolean attendance;

}

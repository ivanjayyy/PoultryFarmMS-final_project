package lk.ijse.poultryfarm.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OwnerDto {

    private String name;
    private String username;
    private String password;
    private String email;

}

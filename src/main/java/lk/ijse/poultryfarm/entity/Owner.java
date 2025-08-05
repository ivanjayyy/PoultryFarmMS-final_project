package lk.ijse.poultryfarm.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Owner {

    private String ownerId;
    private String name;
    private String username;
    private String password;
    private String email;

}

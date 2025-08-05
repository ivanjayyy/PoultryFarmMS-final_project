package lk.ijse.poultryfarm.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Food {

    private String foodId;
    private String foodName;
    private double quantityRemain;
}

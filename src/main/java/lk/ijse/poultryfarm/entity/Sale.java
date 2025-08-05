package lk.ijse.poultryfarm.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Sale {

    private String batchId;
    private String saleId;
    private double totalSale;
    private String date;
    private int chicksSold;

}

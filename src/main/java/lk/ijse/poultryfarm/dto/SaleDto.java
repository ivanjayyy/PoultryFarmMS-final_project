package lk.ijse.poultryfarm.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SaleDto {

    private String batchId;
    private String saleId;
    private double totalSale;
    private String date;
    private int chicksSold;

}

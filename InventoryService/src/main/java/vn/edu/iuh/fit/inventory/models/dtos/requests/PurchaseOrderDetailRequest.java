package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseOrderDetailRequest {
    private Long id;

    private Long medicine;

    private Long category;

    private Long purchaseOrderId;

    private Long quantity;

    private double price;

    private double discount;

    private Timestamp expirationDate;

    private String review;
}

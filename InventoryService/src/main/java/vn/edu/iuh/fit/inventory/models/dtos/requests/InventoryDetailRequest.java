package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryDetailRequest {
    private Long id;

    private Long medicine;

    private Long category;

    private Long inventoryId;

    private Long shelfId;

    private String location;

    private Long quantity;

    private Long price;

    private Timestamp expirationDate;
}

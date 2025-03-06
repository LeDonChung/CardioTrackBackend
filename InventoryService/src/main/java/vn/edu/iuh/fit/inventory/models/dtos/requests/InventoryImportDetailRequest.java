package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryImportDetailRequest {
    private Long id;

    private Long inventoryImportId;

    private Long medicine;

    private Long category;

    private Long quantity;

    private double price;

    private double discount;

    private Timestamp expirationDate;
}

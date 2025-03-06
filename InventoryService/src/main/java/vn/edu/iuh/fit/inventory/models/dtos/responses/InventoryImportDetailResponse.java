package vn.edu.iuh.fit.inventory.models.dtos.responses;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryImportDetailResponse {
    private Long id;

    private Long inventoryImportId;

    private Long medicine;

    private Long category;

    private Long quantity;

    private double price;

    private double discount;

    private Timestamp expirationDate;
}

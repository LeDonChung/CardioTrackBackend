package vn.edu.iuh.fit.inventory.models.dtos.responses;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import vn.edu.iuh.fit.inventory.models.entities.Inventory;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryDetailResponse {
    private Long id;

    private Long medicine;

    private Long category;

    private Long inventoryId;

    private Long shelfId;

    private String location;

    private Long quantity;

    private Long price;

    private Timestamp expirationDate;

    private boolean expired = false;
    private boolean nearExpiration = false;
}

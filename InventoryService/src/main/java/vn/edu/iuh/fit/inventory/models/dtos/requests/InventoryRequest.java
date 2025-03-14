package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.InventoryStatus;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryRequest {
    private Long id;

    private Long totalProduct;

    private Long capacity;

    private Timestamp lastUpdated;

    private InventoryStatus status;

    private Set<ShelfRequest> shelf;
}

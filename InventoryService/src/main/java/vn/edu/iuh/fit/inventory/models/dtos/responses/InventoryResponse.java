package vn.edu.iuh.fit.inventory.models.dtos.responses;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.InventoryStatus;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponse {
    private Long id;

    private Long totalProduct;

    private Long capacity;

    private Timestamp lastUpdated;

    private InventoryStatus status;

    private Set<ShelfResponse> shelf;
}

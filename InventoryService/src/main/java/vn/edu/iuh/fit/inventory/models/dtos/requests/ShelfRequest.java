package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.SheftStatus;
import vn.edu.iuh.fit.inventory.models.entities.Inventory;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShelfRequest {
    private Long id;
    private String location;
    private Long totalProduct;
    private Long capacity;
    private SheftStatus status;
    private String notes;
    private InventoryRequest inventory;
}

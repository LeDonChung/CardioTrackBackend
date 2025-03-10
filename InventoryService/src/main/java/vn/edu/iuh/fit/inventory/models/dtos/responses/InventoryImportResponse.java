package vn.edu.iuh.fit.inventory.models.dtos.responses;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;
import vn.edu.iuh.fit.inventory.models.entities.Inventory;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImportDetail;
import vn.edu.iuh.fit.inventory.models.entities.Supplier;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InventoryImportResponse {
    private Long id;
    private String recipient;
    private Timestamp importDate;
    private InventoryImportStatus status;
    private String notes;
    private Long supplier;
    private Long inventory;
//    private Set<InventoryImportDetail> inventoryImportDetails;
}

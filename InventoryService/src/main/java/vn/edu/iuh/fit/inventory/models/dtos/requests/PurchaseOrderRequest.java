package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrderDetail;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseOrderRequest {
    private Long id;
    private Long supplierId;
    private String supplierName;
    private Timestamp orderDate;
    private PurchaseOrderStatus status;
    private Set<PurchaseOrderDetail> purchaseOrderDetails;
}

package vn.edu.iuh.fit.inventory.models.dtos.responses;

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
public class PurchaseOrderResponse {
    private Long id;
    private Long supplierId;
    private String supplierName;
    private String supplierAddress;
    private String supplierContactInfo;
    private Timestamp orderDate;
    private PurchaseOrderStatus status;
    private Set<PurchaseOrderDetail> purchaseOrderDetails;
}

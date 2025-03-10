package vn.edu.iuh.fit.inventory.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrderDetail;
import vn.edu.iuh.fit.inventory.models.entities.Supplier;

import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseOrderResponse {
    private Long id;
    private String supplierName;
    private Timestamp orderDate;
    private PurchaseOrderStatus status;
    private Set<PurchaseOrderDetail> purchaseOrderDetails;
}

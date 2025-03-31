package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SupplierRequest {
    private Long id;
    private String name;
    private String address;
    private String contactInfo;
    private Set<PurchaseOrder> purchaseOrders;
}

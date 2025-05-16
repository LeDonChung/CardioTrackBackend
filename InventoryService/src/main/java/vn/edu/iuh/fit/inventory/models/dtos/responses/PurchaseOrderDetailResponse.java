package vn.edu.iuh.fit.inventory.models.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseOrderDetailResponse {
    private Long id;

    private Long medicine;

    private Long category;

    private Long purchaseOrderId;

    private Long quantity;

    private double price;

    private double discount;

    private Timestamp expirationDate;

    private String review;
}

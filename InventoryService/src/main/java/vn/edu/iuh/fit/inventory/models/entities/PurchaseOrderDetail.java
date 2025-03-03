package vn.edu.iuh.fit.inventory.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "purchase_order_details")
public class PurchaseOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long medicine;

    private Long category;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    private Long quantity;

    private double price;

    private double discount;

    @Column(name="expiration_date")
    private Timestamp expirationDate;
}

package vn.edu.iuh.fit.inventory.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "inventory_details")
public class InventoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long medicine;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    private String location;

    private Long quantity;

    private double price;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;
}

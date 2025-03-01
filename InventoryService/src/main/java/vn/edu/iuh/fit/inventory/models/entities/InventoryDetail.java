package vn.edu.iuh.fit.inventory.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "iventory_details")
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

    private Long price;

    @Column(name = "expiration_date")
    private String expirationDate;
}

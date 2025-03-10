package vn.edu.iuh.fit.inventory.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "inventory_import_details")
public class InventoryImportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "import_id")
    @JsonIgnore
    private InventoryImport inventoryImport;

    private Long medicine;

    private Long category;

    private Long quantity;

    private double price;

    private double discount;

    @Column(name="expiration_date")
    private Timestamp expirationDate;
}

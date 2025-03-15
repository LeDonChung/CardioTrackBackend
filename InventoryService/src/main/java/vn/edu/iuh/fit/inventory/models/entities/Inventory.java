package vn.edu.iuh.fit.inventory.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.inventory.enums.InventoryStatus;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private Long id;

    @Column(name = "total_product")
    private Long totalProduct;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;


    @OneToMany(mappedBy = "inventory")
    @JsonIgnore
    private Set<Shelf> shelfs = new LinkedHashSet<>();



}

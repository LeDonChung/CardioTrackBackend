package vn.edu.iuh.fit.inventory.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.inventory.enums.SheftStatus;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shelfs")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelf_id", nullable = false)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name="total_product")
    private Long totalProduct;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SheftStatus status;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "inventory_id")
    @JsonIgnore
    private Inventory inventory;
}

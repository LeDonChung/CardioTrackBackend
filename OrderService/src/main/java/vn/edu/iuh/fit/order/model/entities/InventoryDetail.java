package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inventory_details")
public class InventoryDetail {
    @EmbeddedId
    private InventoryDetailId id;

    @MapsId("inventoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @MapsId("medicineId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "medicine_id", nullable = false)
    private vn.edu.iuh.fit.order.model.entities.Medicine medicine;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "location")
    private String location;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
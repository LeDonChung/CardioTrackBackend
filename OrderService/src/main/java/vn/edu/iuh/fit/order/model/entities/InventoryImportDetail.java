package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "inventory_import_details")
public class InventoryImportDetail {
    @EmbeddedId
    private InventoryImportDetailId id;

    @MapsId("importId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "import_id", nullable = false)
    private vn.edu.iuh.fit.order.model.entities.InventoryImport importField;

    @MapsId("medicineId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "medicine_id", nullable = false)
    private vn.edu.iuh.fit.order.model.entities.Medicine medicine;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}
package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "inventory_exports")
public class InventoryExport {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnDefault("current_timestamp()")
    @Column(name = "export_date")
    private Instant exportDate;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Lob
    @Column(name = "notes")
    private String notes;

}
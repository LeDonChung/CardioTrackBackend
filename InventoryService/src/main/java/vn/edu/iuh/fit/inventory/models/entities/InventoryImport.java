package vn.edu.iuh.fit.inventory.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "inventory_imports")
public class InventoryImport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_id", nullable = false)
    private Long id;

    private String recipient;

    @Column(name = "import_date")
    private Timestamp importDate;

    @Enumerated(EnumType.STRING)
    private InventoryImportStatus status;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @OneToMany(mappedBy = "inventoryImport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<InventoryImportDetail> inventoryImportDetails;
}

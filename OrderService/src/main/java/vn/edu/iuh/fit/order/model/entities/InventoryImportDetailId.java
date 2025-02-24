package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class InventoryImportDetailId implements java.io.Serializable {
    private static final long serialVersionUID = 8054327906038535651L;
    @Column(name = "import_id", nullable = false)
    private Long importId;

    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventoryImportDetailId entity = (InventoryImportDetailId) o;
        return Objects.equals(this.importId, entity.importId) &&
                Objects.equals(this.medicineId, entity.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importId, medicineId);
    }

}
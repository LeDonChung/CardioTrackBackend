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
public class InventoryExportDetailId implements java.io.Serializable {
    private static final long serialVersionUID = -5159215483500607475L;
    @Column(name = "export_id", nullable = false)
    private Long exportId;

    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventoryExportDetailId entity = (InventoryExportDetailId) o;
        return Objects.equals(this.medicineId, entity.medicineId) &&
                Objects.equals(this.exportId, entity.exportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, exportId);
    }

}
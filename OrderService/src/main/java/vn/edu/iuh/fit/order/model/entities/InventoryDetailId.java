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
public class InventoryDetailId implements java.io.Serializable {
    private static final long serialVersionUID = -416605668957393332L;
    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventoryDetailId entity = (InventoryDetailId) o;
        return Objects.equals(this.medicineId, entity.medicineId) &&
                Objects.equals(this.inventoryId, entity.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineId, inventoryId);
    }

}
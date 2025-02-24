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
public class PurchaseOrderDetailId implements java.io.Serializable {
    private static final long serialVersionUID = 7131522290135890907L;
    @Column(name = "purchase_order_id", nullable = false)
    private Long purchaseOrderId;

    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PurchaseOrderDetailId entity = (PurchaseOrderDetailId) o;
        return Objects.equals(this.purchaseOrderId, entity.purchaseOrderId) &&
                Objects.equals(this.medicineId, entity.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseOrderId, medicineId);
    }

}
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
public class OrdersDetailId implements java.io.Serializable {
    private static final long serialVersionUID = -509874388548589946L;
    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrdersDetailId entity = (OrdersDetailId) o;
        return Objects.equals(this.orderId, entity.orderId) &&
                Objects.equals(this.medicineId, entity.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, medicineId);
    }

}
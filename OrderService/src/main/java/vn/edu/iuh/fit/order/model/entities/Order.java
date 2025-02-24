package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@Entity
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "export_invoice")
    private Boolean exportInvoice;

    @Column(name = "fee_ship")
    private Double feeShip;

    @Column(name = "note")
    private String note;

    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "status")
    private Byte status;

    @Column(name = "use_point")
    private Boolean usePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private vn.edu.iuh.fit.order.model.entities.User user;

}
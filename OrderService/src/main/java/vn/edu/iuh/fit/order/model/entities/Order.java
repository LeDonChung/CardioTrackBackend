package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.order.enums.OrderStatus;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;

    @Column(name = "export_invoice")
    private boolean exportInvoice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "fee_ship")
    private double feeShip;

    private Long customer;

    private Long addressDetail;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    // Getters and Setters
}

package vn.edu.iuh.fit.order.model.entities;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int discount;

    private double price;

    private int quantity;

    private Long medichine;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}

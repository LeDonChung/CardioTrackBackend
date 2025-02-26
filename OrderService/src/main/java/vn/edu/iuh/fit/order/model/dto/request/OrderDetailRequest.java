package vn.edu.iuh.fit.order.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailRequest {
    private Long id;

    private int discount;

    private double price;

    private int quantity;

    private Long medicine;

    private Long orderId;
}

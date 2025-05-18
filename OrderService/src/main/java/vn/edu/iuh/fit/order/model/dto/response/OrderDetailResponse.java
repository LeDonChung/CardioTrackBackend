package vn.edu.iuh.fit.order.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDetailResponse {
    private Long id;

    private int discount;

    private double price;

    private int quantity;

    private Long medicine;

    private Long orderId;
}

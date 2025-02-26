package vn.edu.iuh.fit.order.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailResponse {
    private Long id;

    private int discount;

    private double price;

    private int quantity;

    private Long medichine;

    private Long orderId;
}

package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetailRequest {
    private int discount;

    private double price;

    private int quantity;

    private Long medicine;

    private Long orderId;
}

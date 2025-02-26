package vn.edu.iuh.fit.order.model.dto.response;

import lombok.*;
import vn.edu.iuh.fit.order.enums.OrderStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;

    private String note;

    private boolean exportInvoice;

    private OrderStatus status;

    private double feeShip;

    private Long customer;

    private Long addressDetail;

    private Long orderDetailId;

}

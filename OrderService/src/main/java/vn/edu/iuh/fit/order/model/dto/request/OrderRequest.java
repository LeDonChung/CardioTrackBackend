package vn.edu.iuh.fit.order.model.dto.request;

import lombok.*;
import vn.edu.iuh.fit.order.enums.OrderStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    private Long id;

    private String note;

    private boolean exportInvoice;

    private OrderStatus status;

    private double feeShip;

    private Long customer;

    private Long addressDetail;

    private Long orderDetailId;

}

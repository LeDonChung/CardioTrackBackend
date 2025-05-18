package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.*;
import vn.edu.iuh.fit.notification.model.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequest {
    private String note;

    private boolean exportInvoice;

    private OrderStatus status;

    private LocalDate orderDate;

    private double feeShip;

    private UserRequest customer;

    private AddressRequest addressDetail;

    private List<OrderDetailRequest> orderDetails;
}

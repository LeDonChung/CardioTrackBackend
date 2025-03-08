package vn.edu.iuh.fit.order.model.dto.request;

import lombok.*;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.enums.PaymentMethod;

import java.time.LocalDate;
import java.util.Set;

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

    private PaymentMethod paymentMethod;

    private LocalDate orderDate;

    private double feeShip;

    private Long customer;

    private AddressRequest addressDetail;

    private Set<OrderDetailRequest> orderDetails;

}

package vn.edu.iuh.fit.notification.model.dto.response;

import lombok.*;
import vn.edu.iuh.fit.notification.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.notification.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.notification.model.dto.request.UserRequest;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class OrderResponse {
    private Long id;

    private String note;

    private boolean exportInvoice;

    private LocalDate orderDate;

    private double feeShip;

    private UserRequest customer;

    private AddressRequest addressDetail;

    private List<OrderDetailRequest> orderDetails;

    private String nameProduct;

    private String imageUrl;

    private Long productId;

    private String init;




}

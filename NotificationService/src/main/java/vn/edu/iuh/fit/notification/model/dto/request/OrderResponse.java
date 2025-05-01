package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponse {
    private Long id;

    private String note;

    private boolean exportInvoice;

    private LocalDate orderDate;

    private double feeShip;

    private UserRequest customer;

    private AddressDetailRequest addressDetail;

    private List<OrderDetailRequest> orderDetails;

    private String nameProduct;

    private String imageUrl;

    private Long productId;

    private String init;




}

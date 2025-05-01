package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequest {
    private UserRequest customer;
    private AddressDetailRequest addressDetail;
    private List<OrderDetailRequest> orderDetails;
    private double feeShip;
    private String note;
    private LocalDate orderDate;
}

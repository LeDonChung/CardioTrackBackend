package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private UserRequest customer;
    private AddressDetailRequest addressDetail;
    private List<OrderDetailRequest> orderDetails;
    private double feeShip;
    private String note;
    private LocalDate orderDate;
}

package vn.edu.iuh.fit.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private List<ProductRequest> products;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private int amount;
    private Long orderCode;
}

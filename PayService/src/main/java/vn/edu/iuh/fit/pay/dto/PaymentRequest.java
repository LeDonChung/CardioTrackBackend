package vn.edu.iuh.fit.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String productName;
    private String description;
    private String returnUrl;
    private String cancelUrl;
    private int amount;
}

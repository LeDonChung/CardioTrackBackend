package vn.edu.iuh.fit.notification.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PaymentMethod {
    CASH("CASH"),
    QR_CODE("QR_CODE"),
    CREDIT_CARD("CREDIT_CARD"),
    BANK_TRANSFER("BANK_TRANSFER"),
    OTHER("OTHER");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }
}

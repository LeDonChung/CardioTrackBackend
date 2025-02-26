package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    private MedicineRequest medicine;
    private int quantity;
    private double price;
    private double discount;
}

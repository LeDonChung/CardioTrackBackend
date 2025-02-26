package vn.edu.iuh.fit.product.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.product.models.entities.Medicine;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineImageRequest {
    private Long id;

    private String url;

    private Long medicineId;
}

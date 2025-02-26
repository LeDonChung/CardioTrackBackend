package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineImageResponse {
    private Long id;

    private String url;

    private Long medicineId;
}

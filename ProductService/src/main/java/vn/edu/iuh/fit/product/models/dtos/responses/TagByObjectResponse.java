package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagByObjectResponse {
    private Long id;

    private String title;

    private String des;

    private Set<MedicineResponse> medicines;
}

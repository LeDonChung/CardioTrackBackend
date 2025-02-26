package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SpecificationResponse {
    private Long id;

    private String title;
}

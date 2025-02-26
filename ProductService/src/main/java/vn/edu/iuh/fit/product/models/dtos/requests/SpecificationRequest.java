package vn.edu.iuh.fit.product.models.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SpecificationRequest {
    private Long id;

    private String title;
}

package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BrandResponse {
    private Long id;

    private String image;

    private String title;
}

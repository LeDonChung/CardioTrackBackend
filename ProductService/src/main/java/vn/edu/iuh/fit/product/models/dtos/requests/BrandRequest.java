package vn.edu.iuh.fit.product.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.product.models.entities.Medicine;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BrandRequest {
    private Long id;

    private String image;

    private String title;
}

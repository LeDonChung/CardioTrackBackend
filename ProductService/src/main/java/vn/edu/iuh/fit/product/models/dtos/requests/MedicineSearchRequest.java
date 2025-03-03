package vn.edu.iuh.fit.product.models.dtos.requests;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineSearchRequest {
    private List<Long> categories;
    private List<Long> brands;
    private Double priceFrom;
    private Double priceTo;
    private List<Long> objects;
}

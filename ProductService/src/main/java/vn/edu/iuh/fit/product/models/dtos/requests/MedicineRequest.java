package vn.edu.iuh.fit.product.models.dtos.requests;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.product.models.entities.*;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineRequest {
    private Long id;
    private String des;
    private String desShort;
    private Integer discount;
    private String init;
    private String name;
    private Double price;
    private String primaryImage;
    private String sku;
    private Integer status;
    private BrandRequest brand;
    private Set<CategoryRequest> categories;
    private Set<SpecificationRequest> specifications;
    private Set<ImageRequest> images;
    private Set<TagRequest> tags;
}

package vn.iuh.edu.fit.consult.models.response;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineResponse {
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
    private BrandResponse brand;
    private Set<CategoryResponse> categories;
    private Set<SpecificationResponse> specifications;
    private Set<ImageResponse> images;
    private Set<TagResponse> tags;
}

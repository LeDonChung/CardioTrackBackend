package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;

    private String fullPathSlug;

    private String icon;

    private int level;

    private String title;

    private Long parentId;
}

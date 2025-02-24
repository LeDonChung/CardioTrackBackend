package vn.edu.iuh.fit.product.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
    private Long id;

    private String fullPathSlug;

    private String icon;

    private int level;

    private String title;

    private Long parentId;
}

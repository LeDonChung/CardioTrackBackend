package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryProminentResponse {
    private Long id;

    private String fullPathSlug;

    private String icon;

    private int level;

    private String title;

    private Long parentId;

    private int num;
}

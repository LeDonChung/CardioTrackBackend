package vn.edu.iuh.fit.inventory.models.dtos.responses;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {
    private Long id;
    private String fullPathSlug;
    private String icon;
    private int level;
    private String title;
    private Long parent;
    private List<Object> children;
}

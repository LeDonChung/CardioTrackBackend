package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    private Long id;

    private String title;

    private String des;
}

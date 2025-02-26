package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ImageResponse {
    private Long id;
    private String url;
}
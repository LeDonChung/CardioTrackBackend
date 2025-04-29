package vn.iuh.edu.fit.consult.models.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BrandResponse {
    private Long id;

    private String image;

    private String title;
}

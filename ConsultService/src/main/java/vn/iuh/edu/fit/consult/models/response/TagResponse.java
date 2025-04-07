package vn.iuh.edu.fit.consult.models.response;

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

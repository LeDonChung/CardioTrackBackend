package vn.iuh.edu.fit.consult.models.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchDataResponse {
    private List<String> productIds;
}

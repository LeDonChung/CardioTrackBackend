package vn.edu.iuh.fit.product.models.dtos.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String password;
}

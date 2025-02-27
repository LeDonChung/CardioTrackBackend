package vn.edu.iuh.fit.product.models.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRegisterRequest {
    private String username;
    private String password;
}

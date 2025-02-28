package vn.edu.iuh.fit.inventory.models.dtos.responses;

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

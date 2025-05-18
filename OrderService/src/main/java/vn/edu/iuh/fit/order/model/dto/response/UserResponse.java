package vn.edu.iuh.fit.order.model.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    private String username;
    private String password;
    private String email;
    private String fullName;
}

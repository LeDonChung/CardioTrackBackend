package vn.edu.iuh.fit.user.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {
    private String username;
    private String password;
    private String rePassword;
    private String fullName;
    private String otp;
}

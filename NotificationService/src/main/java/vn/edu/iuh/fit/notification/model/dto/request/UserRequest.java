package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {
    private String fullName;
    private String email;
    private String username;

}

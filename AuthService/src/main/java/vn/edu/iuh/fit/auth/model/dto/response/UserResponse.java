package vn.edu.iuh.fit.auth.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.auth.model.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;

    private String password;

    private String fullName;

    private Gender gender;

    private LocalDate dob;

    private Boolean enabled;

    private Boolean verify;
}

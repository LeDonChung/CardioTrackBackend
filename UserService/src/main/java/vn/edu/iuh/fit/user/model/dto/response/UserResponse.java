package vn.edu.iuh.fit.user.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.user.model.enums.Gender;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;

    private String password;
    private String email;

    private String fullName;

    private Gender gender;

    private LocalDate dob;

    private Boolean enabled;

    private Boolean verify;

    private Set<String> roleNames;
}

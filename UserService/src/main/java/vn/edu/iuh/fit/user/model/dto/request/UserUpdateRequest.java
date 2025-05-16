package vn.edu.iuh.fit.user.model.dto.request;

import lombok.*;
import vn.edu.iuh.fit.user.model.entity.Role;
import vn.edu.iuh.fit.user.model.enums.Gender;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    private String fullName;
    private String username;
    private Gender gender;
    private LocalDate dob;
}

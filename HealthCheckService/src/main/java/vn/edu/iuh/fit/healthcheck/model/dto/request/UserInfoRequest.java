package vn.edu.iuh.fit.healthcheck.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {
    private String fullName;  // Họ và tên người dùng
    private String phone;  // Số điện thoại
    private String email;  // Email của người dùng (không bắt buộc)
    private LocalDate dateOfBirth;  // Ngày sinh
}

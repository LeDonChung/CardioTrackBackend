package vn.edu.iuh.fit.healthcheck.model.dto.response;

import java.time.LocalDate;

public class UserInfoResponse {
    private Long id;
    private String fullName;  // Họ và tên người dùng
    private String phone;  // Số điện thoại
    private String email;  // Email của người dùng
    private LocalDate dateOfBirth;  // Ngày sinh
}

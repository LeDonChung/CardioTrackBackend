package vn.edu.iuh.fit.healthcheck.model.dto.request;

import java.time.LocalDate;

public class UserInfoRequest {
    private String fullName;  // Họ và tên người dùng
    private String phone;  // Số điện thoại
    private String email;  // Email của người dùng (không bắt buộc)
    private LocalDate dateOfBirth;  // Ngày sinh
}

package vn.edu.iuh.fit.healthcheck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.healthcheck.model.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    // Có thể thêm các phương thức truy vấn nếu cần thiết
}
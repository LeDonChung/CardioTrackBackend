package vn.edu.iuh.fit.healthcheck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.healthcheck.model.entity.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    // Có thể thêm các phương thức truy vấn nếu cần thiết
}
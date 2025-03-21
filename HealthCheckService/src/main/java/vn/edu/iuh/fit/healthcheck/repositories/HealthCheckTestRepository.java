package vn.edu.iuh.fit.healthcheck.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.healthcheck.model.entity.HealthCheckTest;

public interface HealthCheckTestRepository  extends JpaRepository<HealthCheckTest, Long> {
}

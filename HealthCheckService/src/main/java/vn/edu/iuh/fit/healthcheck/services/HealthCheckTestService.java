package vn.edu.iuh.fit.healthcheck.services;

import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;

import java.util.List;
public interface HealthCheckTestService {
    // Tạo bài kiểm tra mới
    HealthCheckTestResponse createHealthCheckTest(HealthCheckTestRequest request);

    // Lấy tất cả bài kiểm tra
    List<HealthCheckTestResponse> getAllHealthCheckTests();

    // Lấy bài kiểm tra theo ID
    HealthCheckTestResponse getHealthCheckTestById(Long id);
}
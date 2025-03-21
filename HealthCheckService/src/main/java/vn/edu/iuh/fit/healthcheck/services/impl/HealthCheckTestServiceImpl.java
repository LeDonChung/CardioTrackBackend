package vn.edu.iuh.fit.healthcheck.services.impl;

import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;
import vn.edu.iuh.fit.healthcheck.mappers.HealthCheckMapper;
import vn.edu.iuh.fit.healthcheck.model.entity.HealthCheckTest;
import vn.edu.iuh.fit.healthcheck.repositories.HealthCheckTestRepository;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HealthCheckTestServiceImpl implements HealthCheckTestService {

    @Autowired
    private HealthCheckTestRepository healthCheckTestRepository;

    @Autowired
    private HealthCheckMapper healthCheckMapper;

    @Override
    public HealthCheckTestResponse createHealthCheckTest(HealthCheckTestRequest request) {
        HealthCheckTest healthCheckTest = healthCheckMapper.toEntity(request);  // Chuyển DTO request thành entity
        HealthCheckTest savedHealthCheckTest = healthCheckTestRepository.save(healthCheckTest);  // Lưu vào cơ sở dữ liệu
        return healthCheckMapper.toResponseDTO(savedHealthCheckTest);  // Trả về DTO
    }

    @Override
    public List<HealthCheckTestResponse> getAllHealthCheckTests() {
        List<HealthCheckTest> tests = healthCheckTestRepository.findAll();
        return tests.stream()
                .map(healthCheckMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HealthCheckTestResponse getHealthCheckTestById(Long id) {
        HealthCheckTest healthCheckTest = healthCheckTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Health Check Test not found"));
        return healthCheckMapper.toResponseDTO(healthCheckTest);
    }
}
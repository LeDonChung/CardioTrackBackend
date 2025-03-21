package vn.edu.iuh.fit.healthcheck.model.dto.request;

import java.util.List;

public class HealthCheckTestRequest {
    private String testName;  // Tên bài kiểm tra
    private String description;  // Mô tả bài kiểm tra
    private List<Long> questionIds;
}

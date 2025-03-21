package vn.edu.iuh.fit.healthcheck.model.dto.response;

import java.util.List;

public class HealthCheckTestResponse {
    private Long id;
    private String testName;  // Tên bài kiểm tra
    private String description;  // Mô tả bài kiểm tra
    private List<QuestionResponse> questions;
}

package vn.edu.iuh.fit.healthcheck.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckTestRequest {
    private String testName;  // Tên bài kiểm tra
    private String description;  // Mô tả bài kiểm tra
    private List<QuestionRequest> questionRequests;
}

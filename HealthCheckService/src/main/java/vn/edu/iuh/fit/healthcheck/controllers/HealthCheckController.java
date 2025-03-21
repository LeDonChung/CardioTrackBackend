package vn.edu.iuh.fit.healthcheck.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.healthcheck.exceptions.HealthException;
import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;
import vn.edu.iuh.fit.healthcheck.services.UserAnswerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/healthcheck")
public class HealthCheckController {

    @Autowired
    private HealthCheckTestService healthCheckTestService;
    @Autowired
    private UserAnswerService userAnswerService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<HealthCheckTestResponse>> createHealthCheckTest(
            @RequestBody HealthCheckTestRequest healthCheckTestRequest) {
        HealthCheckTestResponse test = healthCheckTestService.createHealthCheckTest(healthCheckTestRequest);
        return ResponseEntity.ok(new BaseResponse<>(test, true, "Created successfully"));
    }

    //lưu test của user
    @PostMapping("/submit-answers")
    public ResponseEntity<BaseResponse<List<UserAnswerResponse>>> submitUserAnswers(
            @RequestBody List<UserAnswerRequest> userAnswers) throws HealthException {

        // Lưu câu trả lời từ người dùng và trả về danh sách câu trả lời đã lưu
        List<UserAnswerResponse> savedAnswers = (List<UserAnswerResponse>) userAnswerService.saveUserAnswer(userAnswers);

        // Trả về danh sách các câu trả lời đã lưu trong BaseResponse
        BaseResponse<List<UserAnswerResponse>> response = new BaseResponse<>(savedAnswers, true, "Answers saved successfully");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<List<HealthCheckTestResponse>> getAllHealthCheckTests() {
        List<HealthCheckTestResponse> tests = healthCheckTestService.getAllHealthCheckTests();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthCheckTestResponse> getHealthCheckTestById(@PathVariable Long id) {
        HealthCheckTestResponse test = healthCheckTestService.getHealthCheckTestById(id);
        return ResponseEntity.ok(test);
    }
}
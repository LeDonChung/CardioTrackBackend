package vn.edu.iuh.fit.healthcheck.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.healthcheck.exceptions.HealthException;
import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.QuestionResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;
import vn.edu.iuh.fit.healthcheck.model.entity.HealthCheckTest;
import vn.edu.iuh.fit.healthcheck.model.entity.Question;
import vn.edu.iuh.fit.healthcheck.model.entity.UserAnswer;
import vn.edu.iuh.fit.healthcheck.repositories.HealthCheckTestRepository;
import vn.edu.iuh.fit.healthcheck.repositories.QuestionRepository;
import vn.edu.iuh.fit.healthcheck.repositories.UserAnswerRepository;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckService;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;
import vn.edu.iuh.fit.healthcheck.services.UserAnswerService;
import vn.edu.iuh.fit.healthcheck.utils.SystemConstraints;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/healthcheck")
public class HealthCheckController {

    @Autowired
    private HealthCheckTestService healthCheckTestService;
    @Autowired
    private UserAnswerService userAnswerService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private HealthCheckTestRepository healthCheckTestRepository;
    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private HealthCheckService healthCheckService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<HealthCheckTestResponse>> createHealthCheckTest(
            @RequestBody HealthCheckTestRequest healthCheckTestRequest) {
        HealthCheckTestResponse test = healthCheckTestService.createHealthCheckTest(healthCheckTestRequest);
        return ResponseEntity.ok(new BaseResponse<>(test, true, "Created successfully"));
    }

    //lưu test của user
    @PostMapping("/submit-answers")
    public ResponseEntity<String> submitUserAnswers(@RequestBody List<UserAnswerRequest> userAnswers) {
        try {
            // Tạo danh sách chứa các đối tượng UserAnswer
            List<UserAnswer> userAnswerList = new ArrayList<>();

            // Chuyển đổi từ UserAnswerRequest sang UserAnswer và lưu vào cơ sở dữ liệu
            for (UserAnswerRequest answer : userAnswers) {
                UserAnswer userAnswer = new UserAnswer();
                userAnswer.setUserId(answer.getUserId());
                userAnswer.setQuestion_id(answer.getQuestionId());
                userAnswer.setAnswer(answer.getAnswer());

                // Lấy thông tin câu hỏi từ question repository
                Question question = questionRepository.findById(answer.getQuestionId())
                        .orElseThrow(() -> new HealthException(SystemConstraints.QUESTION_NOT_FOUND));

                // Lấy các lựa chọn cho câu hỏi từ bảng question_choices
                List<String> choices = question.getChoices();

                // Kiểm tra câu trả lời của người dùng có hợp lệ không
                if (choices.contains(answer.getAnswer())) {
                    // Nếu câu trả lời hợp lệ, lưu câu trả lời vào database
                    HealthCheckTest test = healthCheckTestRepository.findById(question.getTest().getId())
                            .orElseThrow(() -> new HealthException(SystemConstraints.QUESTION_NOT_FOUND));
                    userAnswer.setTestTitle(test.getTestName());  // Lưu tiêu đề bài kiểm tra
                    userAnswer.setQuestionTitle(question.getQuestionTitle());  // Lưu tiêu đề câu hỏi
                    userAnswerRepository.save(userAnswer); // Lưu vào cơ sở dữ liệu

                    // Thêm câu trả lời vào danh sách đã lưu
                    userAnswerList.add(userAnswer);
                } else {
                    throw new RuntimeException("Invalid answer for question ID: " + answer.getQuestionId());
                }
            }

            // Xây dựng thông điệp từ câu trả lời của người dùng
            StringBuilder userMessage = new StringBuilder();
            for (UserAnswer userAnswer : userAnswerList) {
                userMessage.append("Câu hỏi: ").append(userAnswer.getQuestionTitle())
                        .append("\nCâu trả lời: ").append(userAnswer.getAnswer())
                        .append("\n");
            }

            // Gửi dữ liệu cho GPT-4 và nhận phản hồi
            String gptResponse = healthCheckService.sendHealthCheckQuestionToGPT(userMessage.toString(), userAnswerList);

            // Trả về phản hồi từ GPT
            return ResponseEntity.ok(gptResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
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
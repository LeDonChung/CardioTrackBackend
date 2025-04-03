package vn.edu.iuh.fit.healthcheck.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.healthcheck.exceptions.HealthException;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;
import vn.edu.iuh.fit.healthcheck.mappers.HealthCheckMapper;
import vn.edu.iuh.fit.healthcheck.model.entity.HealthCheckTest;
import vn.edu.iuh.fit.healthcheck.model.entity.Question;
import vn.edu.iuh.fit.healthcheck.model.entity.UserAnswer;
import vn.edu.iuh.fit.healthcheck.repositories.HealthCheckTestRepository;
import vn.edu.iuh.fit.healthcheck.repositories.QuestionRepository;
import vn.edu.iuh.fit.healthcheck.repositories.UserAnswerRepository;
import vn.edu.iuh.fit.healthcheck.services.UserAnswerService;
import vn.edu.iuh.fit.healthcheck.utils.SystemConstraints;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private HealthCheckMapper healthCheckMapper;

    @Autowired
    private HealthCheckTestRepository healthCheckTestRepository;

    @Override
    public List<UserAnswerResponse> saveUserAnswer(List<UserAnswerRequest> userAnswers) throws HealthException {
        List<UserAnswerResponse> savedAnswers = new ArrayList<>();

        for (UserAnswerRequest request : userAnswers) {
            // Tạo đối tượng UserAnswer mới
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setUserId(request.getUserId());
            userAnswer.setQuestion_id(request.getQuestionId());
            userAnswer.setAnswer(request.getAnswer());

            // Lấy thông tin câu hỏi từ question repository
            Question question = questionRepository.findById(request.getQuestionId())
                    .orElseThrow(() -> new HealthException(SystemConstraints.QUESTION_NOT_FOUND));

            // Lấy các lựa chọn cho câu hỏi từ bảng question_choices
            List<String> choices = question.getChoices();

            // Kiểm tra câu trả lời của người dùng có hợp lệ không
            if (choices.contains(request.getAnswer())) {
                // Nếu câu trả lời hợp lệ, lưu câu trả lời vào database
                HealthCheckTest test = healthCheckTestRepository.findById(question.getTest().getId())
                        .orElseThrow(() -> new HealthException(SystemConstraints.QUESTION_NOT_FOUND));
                userAnswer.setTestTitle(test.getTestName());  // Lưu tiêu đề bài kiểm tra

                userAnswer.setQuestionTitle(question.getQuestionTitle());  // Lưu tiêu đề câu hỏi

                // Lưu câu trả lời vào cơ sở dữ liệu
                userAnswerRepository.save(userAnswer);

                // Thêm câu trả lời vào danh sách đã lưu
                savedAnswers.add(healthCheckMapper.toResponseDTO(userAnswer)); // Chuyển thành DTO và thêm vào danh sách
            } else {
                throw new RuntimeException("Invalid answer for question ID: " + request.getQuestionId());
            }
        }
        return savedAnswers; // Trả về danh sách câu trả lời đã lưu
    }

    @Override
    public UserAnswerResponse getUserAnswerById(Long id) {
        UserAnswer userAnswer = userAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User answer not found"));
        return healthCheckMapper.toResponseDTO(userAnswer);
    }
}

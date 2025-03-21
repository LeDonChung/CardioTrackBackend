package vn.edu.iuh.fit.healthcheck.services.impl;

import vn.edu.iuh.fit.healthcheck.exceptions.HealthException;
import vn.edu.iuh.fit.healthcheck.model.entity.Question;
import vn.edu.iuh.fit.healthcheck.repositories.QuestionRepository;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;
import vn.edu.iuh.fit.healthcheck.mappers.HealthCheckMapper;
import vn.edu.iuh.fit.healthcheck.model.entity.HealthCheckTest;
import vn.edu.iuh.fit.healthcheck.repositories.HealthCheckTestRepository;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;
import vn.edu.iuh.fit.healthcheck.utils.SystemConstraints;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HealthCheckTestServiceImpl implements HealthCheckTestService {

    @Autowired
    private HealthCheckTestRepository healthCheckTestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private HealthCheckMapper healthCheckMapper;

    @Override
    public HealthCheckTestResponse createHealthCheckTest(HealthCheckTestRequest request) {
        // Tạo bài kiểm tra
        HealthCheckTest healthCheckTest = healthCheckMapper.toEntity(request);
        HealthCheckTest savedHealthCheckTest = healthCheckTestRepository.save(healthCheckTest);

        // Tạo câu hỏi và gán chúng vào bài kiểm tra
        List<Question> questions = request.getQuestionRequests().stream()
                .map(questionRequest -> {
                    Question question = new Question();
                    question.setQuestionTitle(questionRequest.getQuestionTitle());
                    question.setChoices(questionRequest.getChoices()); // Gán lựa chọn từ yêu cầu Postman

                    question.setTest(savedHealthCheckTest);  // Gán bài kiểm tra cho câu hỏi
                    return question;
                }).collect(Collectors.toList());
        // Lưu lại các câu hỏi đã liên kết với bài kiểm tra
        questionRepository.saveAll(questions);

        // Trả về thông tin bài kiểm tra đã tạo
        return healthCheckMapper.toResponseDTO(savedHealthCheckTest);
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
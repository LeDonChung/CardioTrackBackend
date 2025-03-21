package vn.edu.iuh.fit.healthcheck.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.healthcheck.model.dto.request.QuestionRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.QuestionResponse;
import vn.edu.iuh.fit.healthcheck.mappers.HealthCheckMapper;
import vn.edu.iuh.fit.healthcheck.model.entity.Question;
import vn.edu.iuh.fit.healthcheck.repositories.QuestionRepository;
import vn.edu.iuh.fit.healthcheck.services.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private HealthCheckMapper healthCheckMapper;

    @Override
    public QuestionResponse createQuestion(QuestionRequest request) {
        Question question = healthCheckMapper.toEntity(request);
        Question savedQuestion = questionRepository.save(question);
        return healthCheckMapper.toResponseDTO(savedQuestion);
    }

    @Override
    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return healthCheckMapper.toResponseDTO(question);
    }

    @Override
    public List<QuestionResponse> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(healthCheckMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
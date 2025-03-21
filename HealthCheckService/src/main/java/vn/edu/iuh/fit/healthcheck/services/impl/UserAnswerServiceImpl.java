package vn.edu.iuh.fit.healthcheck.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;
import vn.edu.iuh.fit.healthcheck.mappers.HealthCheckMapper;
import vn.edu.iuh.fit.healthcheck.model.entity.UserAnswer;
import vn.edu.iuh.fit.healthcheck.repositories.UserAnswerRepository;
import vn.edu.iuh.fit.healthcheck.services.UserAnswerService;

@Service
public class UserAnswerServiceImpl implements UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private HealthCheckMapper healthCheckMapper;

    @Override
    public UserAnswerResponse saveUserAnswer(UserAnswerRequest request) {
        UserAnswer userAnswer = healthCheckMapper.toEntity(request);
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
        return healthCheckMapper.toResponseDTO(savedUserAnswer);
    }

    @Override
    public UserAnswerResponse getUserAnswerById(Long id) {
        UserAnswer userAnswer = userAnswerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User answer not found"));
        return healthCheckMapper.toResponseDTO(userAnswer);
    }
}

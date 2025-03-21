package vn.edu.iuh.fit.healthcheck.services;


import vn.edu.iuh.fit.healthcheck.exceptions.HealthException;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;

import java.util.List;

public interface UserAnswerService {
    // Lưu câu trả lời người dùng
    List<UserAnswerResponse> saveUserAnswer(List<UserAnswerRequest> userAnswers) throws HealthException;

    // Lấy câu trả lời của người dùng
    UserAnswerResponse getUserAnswerById(Long id);
}
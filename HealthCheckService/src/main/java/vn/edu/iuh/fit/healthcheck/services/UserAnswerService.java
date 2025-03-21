package vn.edu.iuh.fit.healthcheck.services;


import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;

public interface UserAnswerService {
    // Lưu câu trả lời người dùng
    UserAnswerResponse saveUserAnswer(UserAnswerRequest request);

    // Lấy câu trả lời của người dùng
    UserAnswerResponse getUserAnswerById(Long id);
}
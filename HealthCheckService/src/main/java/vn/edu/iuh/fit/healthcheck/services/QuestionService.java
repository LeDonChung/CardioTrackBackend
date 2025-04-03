package vn.edu.iuh.fit.healthcheck.services;

import vn.edu.iuh.fit.healthcheck.model.dto.request.QuestionRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    // Tạo câu hỏi mới
    QuestionResponse createQuestion(QuestionRequest request);

    // Lấy câu hỏi theo ID
    QuestionResponse getQuestionById(Long id);

    // Lấy tất cả câu hỏi
    List<QuestionResponse> getAllQuestions();
}
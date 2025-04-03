package vn.edu.iuh.fit.healthcheck.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerResponse {
    private Long id;  // ID của câu trả lời
    private Long userId;  // ID người dùng
    private Long questionId;  // ID câu hỏi
    private String answer;  // Câu trả lời người dùng đã chọn
    private String questionTitle;  // Tiêu đề câu hỏi
    private String testTitle;  // Tiêu đề bài kiểm tra
}

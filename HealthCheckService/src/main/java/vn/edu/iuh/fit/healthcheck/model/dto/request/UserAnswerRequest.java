package vn.edu.iuh.fit.healthcheck.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerRequest {
    private Long userId;  // ID người dùng
    private Long questionId;  // ID câu hỏi
    private String answer;  // Câu trả lời của người dùng
    private String choice;  // Lựa chọn của người dùng
}

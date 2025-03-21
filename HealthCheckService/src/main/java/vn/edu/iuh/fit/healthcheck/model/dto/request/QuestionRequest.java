package vn.edu.iuh.fit.healthcheck.model.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    private String questionTitle;  // Nội dung câu hỏi
    private List<String> choices; // Các lựa chọn cho câu hỏi (ví dụ: "Nam", "Nữ")

}

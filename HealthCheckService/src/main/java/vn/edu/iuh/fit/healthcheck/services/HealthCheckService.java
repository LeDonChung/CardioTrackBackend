package vn.edu.iuh.fit.healthcheck.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.healthcheck.exceptions.HealthException;
import vn.edu.iuh.fit.healthcheck.model.dto.request.GPTRequest;
import vn.edu.iuh.fit.healthcheck.model.entity.Question;
import vn.edu.iuh.fit.healthcheck.model.entity.UserAnswer;
import vn.edu.iuh.fit.healthcheck.repositories.QuestionRepository;

import java.util.List;


@Service
public class HealthCheckService {


    @Getter
    @Setter
private String OPENAI_API_KEY;
    public HealthCheckService() {
        Dotenv dotenv = Dotenv.load();  // Nạp các biến môi trường từ .env file
        this.OPENAI_API_KEY = dotenv.get("OPENAI_API_KEY");  // Lấy giá trị của OPENAI_API_KEY từ .env file
    }

    public String getOpenAIKey() {
        return OPENAI_API_KEY;
    }


    @Autowired
    private QuestionRepository questionRepository;

    public String sendHealthCheckQuestionToGPT(String userMessage, List<UserAnswer> userAnswers) throws Exception {
        // Tạo ObjectMapper để chuyển đối tượng thành JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Tạo thông điệp yêu cầu GPT trả về kết quả chi tiết
        StringBuilder prompt = new StringBuilder();
        prompt.append("Như một trợ lý ảo hỗ trợ chuẩn đoán sức khỏe từ bài kiểm tra của người dùng. Dưới đây là các câu trả lời của người dùng. Hãy trả về kết quả ngắn gọn, bao gồm tên bài kiểm tra, câu hỏi, câu trả lời và lời khuyên cụ thể về sức khỏe:\n");
        for (UserAnswer userAnswer : userAnswers) {
            // Lấy thông tin câu hỏi từ câu trả lời của người dùng
            Question question = questionRepository.findById(userAnswer.getQuestion_id())
                    .orElseThrow(() -> new HealthException("Câu hỏi không tồn tại"));

            // Định dạng thông điệp với dữ liệu rõ ràng cho GPT
            prompt.append("Bài kiểm tra: ").append(question.getTest().getTestName())  // Tên bài kiểm tra
                    .append("\nCâu hỏi: ").append(question.getQuestionTitle())  // Tên câu hỏi
                    .append("\nĐáp án: ").append(userAnswer.getAnswer())  // Đáp án người dùng chọn
                    .append("\n\n");
        }

        // Cập nhật thêm yêu cầu phân tích kết quả
        prompt.append("Dựa trên các câu trả lời trên, hãy cung cấp kết quả ngắn gọn với lời khuyên cụ thể về sức khỏe.");
        // Tạo JSON payload cho yêu cầu gửi tới OpenAI API
        String requestBody = objectMapper.writeValueAsString(new GPTRequest("gpt-4o-mini", prompt.toString(), 1000, 0.7));

        // Tạo HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);  // API key được lấy từ cấu hình

        // Gửi yêu cầu POST tới API của OpenAI
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";  // Sử dụng endpoint chính xác

        // Gửi yêu cầu và nhận phản hồi
        String response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();

        return response;
    }
}
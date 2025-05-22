package vn.edu.iuh.fit.healthcheck.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // ID người dùng
    private Long question_id; // ID câu hỏi
    private String answer; // Câu trả lời của người dùng
    // Thêm trường để lưu thông tin tiêu đề câu hỏi
    private String questionTitle; // Tiêu đề câu hỏi

    // Thêm trường để lưu thông tin tiêu đề bài kiểm tra
    private String testTitle;  // Tiêu đề bài kiểm tra
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")  // Trường khóa ngoại
        private UserInfo userInfo;  // Quan hệ với UserInfo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;  // Câu hỏi mà người dùng trả lời

}
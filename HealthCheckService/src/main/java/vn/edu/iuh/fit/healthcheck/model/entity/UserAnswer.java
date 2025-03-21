package vn.edu.iuh.fit.healthcheck.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // ID người dùng
    private Long questionId; // ID câu hỏi
    private String answer; // Câu trả lời của người dùng

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")  // Trường khóa ngoại
    private UserInfo userInfo;  // Quan hệ với UserInfo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;  // Câu hỏi mà người dùng trả lời

}
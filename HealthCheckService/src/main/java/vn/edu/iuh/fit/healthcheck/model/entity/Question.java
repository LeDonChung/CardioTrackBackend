package vn.edu.iuh.fit.healthcheck.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String questionTitle;
    private String questionText; // Nội dung câu hỏi

    @ElementCollection
    @CollectionTable(name = "question_choices", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "choice")
    private List<String> choices; // Các lựa chọn câu hỏi (ví dụ: "<40 tuổi", "40 - 49 tuổi",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private HealthCheckTest test; // Bài kiểm tra mà câu hỏi thuộc về


}
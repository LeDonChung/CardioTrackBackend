package vn.edu.iuh.fit.healthcheck.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "health_check_tests")
public class HealthCheckTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName; // Tên bài kiểm tra
    private String description; // Mô tả bài kiểm tra

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions; // Các câu hỏi trong bài kiểm tra

}

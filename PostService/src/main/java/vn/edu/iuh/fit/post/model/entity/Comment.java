package vn.edu.iuh.fit.post.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @Column(name = "author_id", nullable = false) // Đặt tên cột rõ ràng
    private Long authorId; // Người bình luận (userId)

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)  // Thiết lập quan hệ N-1 với Post
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}

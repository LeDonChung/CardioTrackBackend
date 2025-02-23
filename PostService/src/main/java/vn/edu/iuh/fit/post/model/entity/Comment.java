package vn.edu.iuh.fit.post.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    private Long authorId; // Người bình luận (userId)
    private Long postId;   // Bài viết được bình luận

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)  // Thiết lập quan hệ N-1 với Post
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}

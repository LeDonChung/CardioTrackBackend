package vn.edu.iuh.fit.post.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String content;
    private Long postId;   // Bài viết cần bình luận
    private Long authorId; // Người viết bình luận (User từ UserService)
}
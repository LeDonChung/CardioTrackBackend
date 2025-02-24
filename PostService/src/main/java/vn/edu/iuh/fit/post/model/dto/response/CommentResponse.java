package vn.edu.iuh.fit.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private String authorName;  // Lấy từ UserService
    private String createdAt;
    private Long postId;
}
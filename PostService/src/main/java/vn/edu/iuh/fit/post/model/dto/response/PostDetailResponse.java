package vn.edu.iuh.fit.post.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String createdAt;
    private List<CommentResponse> comments; // Danh sách bình luận của bài viết
}
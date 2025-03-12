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
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long authorId; // Tên người đăng bài (Lấy từ UserService)
    private String fullName; // Tên người đăng bài (Lấy từ UserService)
    private String createdAt;
    private List<CommentResponse> comments;

    private UserResponse userResponse;

    private  int soluongbinhluan;

}
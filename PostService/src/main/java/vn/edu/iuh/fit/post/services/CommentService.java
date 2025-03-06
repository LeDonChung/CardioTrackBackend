package vn.edu.iuh.fit.post.services;

import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.model.dto.request.CommentRequest;
import vn.edu.iuh.fit.post.model.dto.response.CommentResponse;

public interface CommentService {
    //tạo comment
    CommentResponse createComment(CommentRequest commentRequest) throws PostException;
    //cập nhật comment
    CommentResponse updateComment(Long commentId, CommentRequest commentRequest)throws PostException;
    //xóa comment
    void deleteComment(Long commentId)throws PostException;
}

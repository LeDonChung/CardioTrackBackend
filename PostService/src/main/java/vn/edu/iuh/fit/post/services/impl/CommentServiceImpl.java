package vn.edu.iuh.fit.post.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.post.client.UserServiceClient;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.jwt.JwtService;
import vn.edu.iuh.fit.post.mappers.CommentMapper;
import vn.edu.iuh.fit.post.mappers.PostMapper;
import vn.edu.iuh.fit.post.model.dto.request.CommentRequest;
import vn.edu.iuh.fit.post.model.dto.response.CommentResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.dto.response.UserResponse;
import vn.edu.iuh.fit.post.model.entity.Comment;
import vn.edu.iuh.fit.post.model.entity.Post;
import vn.edu.iuh.fit.post.repositories.CommentRepository;
import vn.edu.iuh.fit.post.repositories.PostRepository;
import vn.edu.iuh.fit.post.services.CommentService;
import vn.edu.iuh.fit.post.utils.SystemConstraints;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private CommentRepository commentRepository;


    @Override
    public CommentResponse createComment(CommentRequest commentRequest) throws PostException {
        // Kiểm tra xem bài viết có tồn tại không
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostException(SystemConstraints.POST_NOT_FOUND));

        // Tạo bình luận mới
        Comment comment = commentMapper.toEntity(commentRequest);
        comment.setContent(commentRequest.getContent());
        comment.setPost(post);
        comment.setAuthorId(commentRequest.getAuthorId()); // Lấy từ request hoặc token nếu cần
        comment.setCreatedAt(LocalDateTime.now());  // Lưu thời gian tạo bình luận

        // Lưu bình luận vào cơ sở dữ liệu
        commentRepository.save(comment);
        return commentMapper.toResponse(comment);
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) throws PostException {
        // Kiểm tra xem bình luận có tồn tại không
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostException("Bình luận không tồn tại"));

        // Cập nhật nội dung bình luận
        comment.setContent(commentRequest.getContent());
        // Cập nhật thời gian cập nhật
        comment.setCreatedAt(LocalDateTime.now());

        // Lưu lại bình luận đã cập nhật
        commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    @Override
    public void deleteComment(Long commentId) throws PostException {
        // Kiểm tra xem bình luận có tồn tại không
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new PostException("Bình luận không tồn tại"));

        // Xóa bình luận
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResponse> getAllComment(Long postId) throws PostException {
        // Kiểm tra xem bài viết có tồn tại không
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(SystemConstraints.POST_NOT_FOUND));

        // Lấy tất cả bình luận của bài viết
        List<Comment> comments = post.getComments();

        return comments.stream()
                .map(comment -> {
                    CommentResponse commentResponse = commentMapper.toResponse(comment);
                    UserResponse userResponse = userServiceClient.findUserById(Long.valueOf(commentResponse.getAuthorId())).getBody().getData();
                    PostResponse postResponse = postMapper.toResponse(post);
                    commentResponse.setFullName(userResponse.getFullName());
                    return commentResponse;
                })
                .toList();
    }
}




package vn.edu.iuh.fit.post.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.model.dto.request.CommentRequest;
import vn.edu.iuh.fit.post.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.post.model.dto.response.CommentResponse;
import vn.edu.iuh.fit.post.services.CommentService;
import vn.edu.iuh.fit.post.services.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    //tạo comment
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<CommentResponse>> createComment(
            @RequestBody CommentRequest commentRequest) throws PostException {

        CommentResponse commentResponse= commentService.createComment(commentRequest);

        return ResponseEntity.ok(
                new BaseResponse<>(commentResponse, true, HttpStatus.CREATED.name())
        );
    }

    // Cập nhật bình luận
    @PutMapping("/update/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest) throws PostException {

        CommentResponse commentResponse = commentService.updateComment(commentId, commentRequest);

        return ResponseEntity.ok(
                new BaseResponse<>(commentResponse, true, HttpStatus.OK.name())
        );
    }

    // Xóa bình luận
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<BaseResponse<CommentResponse>> deleteComment(
            @PathVariable Long commentId) throws PostException {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok(
                new BaseResponse<>(null, true, "Deleted successfully")
        );
    }

    @GetMapping("/get-comments/{postId}")
    public ResponseEntity<BaseResponse<List<CommentResponse>>> getComment(
            @PathVariable Long postId) throws PostException {

        List<CommentResponse> commentResponses = postService.findById(postId).getComments();

        return ResponseEntity.ok(
                new BaseResponse<>(commentResponses, true, HttpStatus.OK.name())
        );
    }
}

package vn.edu.iuh.fit.post.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.jwt.JwtService;
import vn.edu.iuh.fit.post.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.services.PostService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(
            @RequestBody PostRequest postRequest) throws PostException {

        PostResponse postResponse = postService.createPost(postRequest);

        return ResponseEntity.ok(
                new BaseResponse<>(postResponse, true, HttpStatus.CREATED.name())
        );
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest) throws PostException {

        PostResponse postResponse = postService.updatePost(postId, postRequest);

        return ResponseEntity.ok(
                new BaseResponse<>(postResponse, true, HttpStatus.OK.name())
        );
    }

    //hàm xóa
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> deletePost(
            @PathVariable Long postId) throws PostException {

        postService.deletePost(postId);

        return ResponseEntity.ok(
                new BaseResponse<>(null, true, "Deleted successfully")
        );
    }

    //haàm tìm kiếm gần đúng theo title
    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<PostResponse>>> searchPosts(
            @RequestParam String title) throws PostException {
        try{
    List<PostResponse> postResponses = postService.searchPosts(title);
    return ResponseEntity.ok(
            new BaseResponse<>(postResponses, true, HttpStatus.OK.name())
    );
        }catch (PostException  e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new BaseResponse<>(null, false, e.getMessage())
            );
}


    }
}

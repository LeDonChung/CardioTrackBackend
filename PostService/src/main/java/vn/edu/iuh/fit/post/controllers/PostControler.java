package vn.edu.iuh.fit.post.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.post.security.JwtService;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.services.PostService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtService jwtService; // Dùng để lấy userId từ JWT

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(
            @RequestBody PostRequest postRequest,
            HttpServletRequest request) {

        // Lấy JWT Token từ header
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtService.extractUsername(token); // Lấy username từ token

        // Gọi API UserService để lấy `authorId` từ username
        Long authorId = postService.getUserIdByUsername(username);

        // Gán authorId vào request
        postRequest.setAuthorId(authorId);

        // Gọi PostService để tạo bài viết
        PostResponse postResponse = postService.createPost(postRequest);

        return ResponseEntity.ok(
                BaseResponse
                        .<PostResponse>builder()
                        .data(postResponse)
                        .code(HttpStatus.CREATED.name())
                        .success(true)
                        .build()
        );
    }
}

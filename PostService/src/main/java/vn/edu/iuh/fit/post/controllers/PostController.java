package vn.edu.iuh.fit.post.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.jwt.JwtService;
import vn.edu.iuh.fit.post.model.dto.reponse.BaseResponse;
import vn.edu.iuh.fit.post.model.dto.reponse.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
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
            HttpServletRequest request) throws PostException {

        // 1️⃣ Lấy token từ request header
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new BaseResponse<>(null, false, "Unauthorized: Missing token")
            );
        }
        token = token.replace("Bearer ", ""); // Loại bỏ "Bearer " khỏi token

        // 2️⃣ Gán token vào postRequest
        postRequest.setToken(token);

        // 3️⃣ Gọi PostService để tạo bài viết
        PostResponse postResponse = postService.createPost(postRequest);

        return ResponseEntity.ok(
                new BaseResponse<>(postResponse, true, HttpStatus.CREATED.name())
        );
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequest postRequest,
            HttpServletRequest request) throws PostException {

        // 1️⃣ Lấy token từ request header
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new BaseResponse<>(null, false, "Unauthorized: Missing token")
            );
        }
        token = token.replace("Bearer ", ""); // Loại bỏ "Bearer " khỏi token

        // 2️⃣ Gán token vào postRequest
        postRequest.setToken(token);

        // 3️⃣ Gọi PostService để cập nhật bài viết
        PostResponse postResponse = postService.updatePost(postId, postRequest, jwtService.extractUserId(token));

        return ResponseEntity.ok(
                new BaseResponse<>(postResponse, true, HttpStatus.OK.name())
        );
    }

    //hàm xóa
    @PostMapping("/delete/{postId}")
    public ResponseEntity<BaseResponse<PostResponse>> deletePost(
            @PathVariable Long postId,
            HttpServletRequest request) throws PostException {

        // 1️⃣ Lấy token từ request header
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new BaseResponse<>(null, false, "Unauthorized: Missing token")
            );
        }
        token = token.replace("Bearer ", ""); // Loại bỏ "Bearer " khỏi token

        // 2️⃣ Gọi PostService để xóa bài viết
        postService.deletePost(postId, jwtService.extractUserId(token));

        return ResponseEntity.ok(
                new BaseResponse<>(null , true, HttpStatus.OK.name())
        );
    }
}

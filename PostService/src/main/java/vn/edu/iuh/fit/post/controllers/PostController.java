package vn.edu.iuh.fit.post.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.jwt.JwtService;
import vn.edu.iuh.fit.post.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import vn.edu.iuh.fit.post.services.S3Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private S3Service s3Service;
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(
            @RequestBody PostRequest postRequest) throws PostException {

        // Kiểm tra các tham số trong postRequest
        if (postRequest.getTitle() == null || postRequest.getTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BaseResponse<>(null, false, "Title is required")
            );
        }

        if (postRequest.getImgTitle() == null || postRequest.getImgTitle().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BaseResponse<>(null, false, "Image URL is required")
            );
        }
        // Tiến hành tạo bài viết
        PostResponse postResponse = postService.createPost(postRequest);

        return ResponseEntity.ok(
                new BaseResponse<>(postResponse, true, HttpStatus.CREATED.name())
        );
    }

    // Trong backend, bạn cần có một endpoint để nhận hình ảnh và upload lên S3.
    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Upload lên S3 và lấy URL
            String imgUrl = s3Service.uploadFile((MultipartFile) file);

            Map<String, String> response = new HashMap<>();
            response.put("url", imgUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "File upload failed"));
        }
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

    //hiển thị danh sách bài viết
    @GetMapping("/all-post")
    public ResponseEntity<BaseResponse<List<PostResponse>>> listPost() throws PostException {
        List<PostResponse> postResponses = postService.getPosts();
        return ResponseEntity.ok(
                new BaseResponse<>(postResponses, true, HttpStatus.OK.name())
        );
    }
    //hiển thị danh sách bài viết của tôi
    @GetMapping("/my-post/{authorId}")
    public ResponseEntity<BaseResponse<List<PostResponse>>> myPosts(@PathVariable Long authorId) throws PostException {
        List<PostResponse> postResponses = postService.getMyPosts(authorId);
        return ResponseEntity.ok(
                new BaseResponse<>(postResponses, true, HttpStatus.OK.name())
        );
    }
}

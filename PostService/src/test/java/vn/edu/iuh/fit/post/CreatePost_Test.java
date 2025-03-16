package vn.edu.iuh.fit.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.mappers.PostMapper;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.entity.Post;
import vn.edu.iuh.fit.post.repositories.PostRepository;
import vn.edu.iuh.fit.post.services.impl.PostServiceImpl;
import vn.edu.iuh.fit.post.utils.SystemConstraints;
import vn.edu.iuh.fit.post.client.UserServiceClient;
import vn.edu.iuh.fit.post.jwt.JwtService;

@ExtendWith(MockitoExtension.class)
public class CreatePost_Test {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private PostServiceImpl postService;

    private PostRequest postRequest;

    @BeforeEach
    void setup() {
        postRequest = new PostRequest();
    }

    @Test
    void testCreatePost_Success() throws Exception {
        postRequest.setTitle("Thuốc bổ thận");
        postRequest.setContent("Tốt cho sức khỏe");

        when(jwtService.getCurrentUser()).thenReturn("0867713557");

        // Mock UserServiceClient trả về userId 5
        BaseResponse<Long> mockResponse = new BaseResponse<>();
        mockResponse.setData(5L);
        when(userServiceClient.findIdByPhoneNumber(any()))
                .thenReturn(ResponseEntity.ok(mockResponse));

        // Mock Post entity
        Post mockPost = new Post();
        mockPost.setTitle("Thuốc bổ thận");
        mockPost.setContent("Tốt cho sức khỏe");
        mockPost.setCreatedAt(LocalDateTime.now());
        mockPost.setAuthorId(5L);

        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        // Mock PostResponse
        PostResponse mockPostResponse = new PostResponse();
        mockPostResponse.setTitle("Thuốc bổ thận");
        mockPostResponse.setContent("Tốt cho sức khỏe");

        when(postMapper.toResponse(any(Post.class))).thenReturn(mockPostResponse);

        PostResponse result = postService.createPost(postRequest);

        assertEquals("Thuốc bổ thận", result.getTitle());
        assertEquals("Tốt cho sức khỏe", result.getContent());
    }

    @Test
    void testCreatePost_EmptyTitle() {
        postRequest.setTitle("");
        postRequest.setContent("Tốt cho sức khỏe");

        PostException e = assertThrows(PostException.class, () ->
                postService.createPost(postRequest)
        );
        assertEquals(SystemConstraints.TITLE_NOT_EMPTY, e.getMessage());
    }

    @Test
    void testCreatePost_TitleExists() {
        postRequest.setTitle("Trợ tim");
        postRequest.setContent("Tốt cho sức khỏe");

        // Giả lập tiêu đề đã tồn tại
        when(postRepository.findByTitleContainingIgnoreCase("Trợ tim")).thenReturn(List.of(new Post()));

        PostException e = assertThrows(PostException.class, () ->
                postService.createPost(postRequest)
        );
        assertEquals("Tiêu đề đã tồn tại, vui lòng nhập tiêu đề khác", e.getMessage());
    }

    @Test
    void testCreatePost_EmptyContent() {
        postRequest.setTitle("tim mạch");
        postRequest.setContent("");

        PostException e = assertThrows(PostException.class, () ->
                postService.createPost(postRequest)
        );
        assertEquals(SystemConstraints.CONTENT_NOT_EMPTY, e.getMessage());
    }

    @Test
    void testCreatePost_LongTitle() {
        postRequest.setTitle("HealthyMindBodyStrongForeverYou");
        postRequest.setContent("Tốt cho sức khỏe");

        PostException e = assertThrows(PostException.class, () ->
                postService.createPost(postRequest)
        );
        assertEquals("Tiêu đề không được vượt quá 30 ký tự", e.getMessage());
    }

    @Test
    void testCreatePost_ValidBoundary() throws Exception {
        postRequest.setTitle("C");
        postRequest.setContent("a");

        when(jwtService.getCurrentUser()).thenReturn("0867713557");

        BaseResponse<Long> mockResponse = new BaseResponse<>();
        mockResponse.setData(5L);
        when(userServiceClient.findIdByPhoneNumber(any()))
                .thenReturn(ResponseEntity.ok(mockResponse));

        Post mockPost = new Post();
        mockPost.setTitle("C");
        mockPost.setContent("a");
        mockPost.setCreatedAt(LocalDateTime.now());
        mockPost.setAuthorId(5L);

        when(postRepository.save(any(Post.class))).thenReturn(mockPost);

        PostResponse mockPostResponse = new PostResponse();
        mockPostResponse.setTitle("C");
        mockPostResponse.setContent("a");

        when(postMapper.toResponse(any(Post.class))).thenReturn(mockPostResponse);

        PostResponse result = postService.createPost(postRequest);

        assertEquals("C", result.getTitle());
        assertEquals("a", result.getContent());
    }
}

package vn.edu.iuh.fit.post.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.iuh.fit.post.client.UserServiceClient;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.jwt.JwtService;
import vn.edu.iuh.fit.post.mappers.PostMapper;
import vn.edu.iuh.fit.post.model.dto.response.CommentResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.dto.response.UserResponse;
import vn.edu.iuh.fit.post.model.entity.Post;
import vn.edu.iuh.fit.post.repositories.PostRepository;
import vn.edu.iuh.fit.post.services.PostService;
import vn.edu.iuh.fit.post.services.S3Service;
import vn.edu.iuh.fit.post.utils.SystemConstraints;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public PostResponse createPost(PostRequest postRequest) throws PostException {

        Long userId = userServiceClient.findIdByPhoneNumber(jwtService.getCurrentUser()).getBody().getData();
        if (postRequest.getImgTitle() == null || postRequest.getImgTitle().isEmpty()) {
            throw new PostException("Image file is missing.");
        }

        // Không cần upload lại ảnh lên S3 ở đây nữa
        // postRequest.getImgTitle() đã chứa URL của ảnh được upload lên S3 từ frontend
        String imgUrl = postRequest.getImgTitle();  // URL của ảnh đã được upload
        Post post = postMapper.toEntity(postRequest);
        post.setImgTitle(imgUrl);
        post.setCreatedAt(LocalDateTime.now()); // Set thời gian tạo bài viết
        post.setAuthorId(userId);  // Gán userId cho bài viết
        post.setComments(null);


        Post savedPost = postRepository.save(post);


        return postMapper.toResponse(savedPost);
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) throws PostException {

        Long userId = userServiceClient.findIdByPhoneNumber(jwtService.getCurrentUser()).getBody().getData();
        if (userId == null) {
            throw new PostException("User not found!");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(SystemConstraints.ACCESS_DENIED));

        // 3️⃣ Kiểm tra user có phải tác giả không
        if (!post.getAuthorId().equals(userId)) {
            throw new PostException(SystemConstraints.NOT_AUTHOR);
        }


        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        return postMapper.toResponse(updatedPost);
    }

    @Override
    public void deletePost(Long postId) throws PostException {

        Long userId = userServiceClient.findIdByPhoneNumber(jwtService.getCurrentUser()).getBody().getData();

        //kiểm tra bài viết có tồn tại không
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(SystemConstraints.POST_NOT_FOUND));

        //  Kiểm tra user có phải tác giả không
        if (!post.getAuthorId().equals(userId)) {
            throw new PostException(SystemConstraints.NOT_AUTHOR);
        }
        // 4️⃣ Xóa bài viết
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostResponse> searchPosts(String title) throws PostException {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(title);
        if (posts.isEmpty()) {
            throw new PostException("Không tìm thấy bài viết với tiêu đề: " + title);
        }
        return posts.stream()
                .map(post -> {
                    // Gọi UserServiceClient để lấy thông tin người tạo bài viết (UserResponse)
                    UserResponse userResponse = userServiceClient.findUserById(post.getAuthorId()).getBody().getData();
                    PostResponse postResponse = postMapper.toResponse(post);
                    postResponse.setFullName(userResponse.getFullName());  // Gán username vào PostResponse
                    return postResponse;
                })
                .toList();
    }

    @Override
    public List<PostResponse> getPosts() throws PostException {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new PostException("Không có bài viết nào.");
        }
        return posts.stream()
                .map(post -> {
                    // Gọi UserServiceClient để lấy thông tin người tạo bài viết (UserResponse)
                    UserResponse userResponse = userServiceClient.findUserById(post.getAuthorId()).getBody().getData();
                    PostResponse postResponse = postMapper.toResponse(post);
                    postResponse.setFullName(userResponse.getFullName());  // Gán username vào PostResponse
                    return postResponse;
                })
                .toList();
    }

    @Override
    public List<PostResponse> getMyPosts(Long authorId) throws PostException {
        authorId = userServiceClient.findIdByPhoneNumber(jwtService.getCurrentUser()).getBody().getData();

        List<Post> posts = postRepository.findByAuthorId(authorId);
        if (posts.isEmpty()) {
            throw new PostException("Không có bài viết nào.");
        }
        return posts.stream()
                .map(post -> {
                    // Gọi UserServiceClient để lấy thông tin người tạo bài viết (UserResponse)
                    UserResponse userResponse = userServiceClient.findUserById(post.getAuthorId()).getBody().getData();
                    PostResponse postResponse = postMapper.toResponse(post);
                    postResponse.setFullName(userResponse.getFullName());  // Gán username vào PostResponse
                    return postResponse;
                })
                .toList();
    }

    @Override
    public PostResponse findById(Long postId) throws PostException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(SystemConstraints.POST_NOT_FOUND));

        // Gọi UserServiceClient để lấy thông tin người tạo bài viết (UserResponse)
        UserResponse userResponse = userServiceClient.findUserById(post.getAuthorId()).getBody().getData();
        PostResponse postResponse = postMapper.toResponse(post);
        List<CommentResponse> commentResponses = postResponse.getComments().stream().map(commentResponse -> {
            // Gọi UserServiceClient để lấy thông tin người tạo bình luận (UserResponse)
            UserResponse commentUserResponse = userServiceClient.findUserById(commentResponse.getAuthorId()).getBody().getData();
            commentResponse.setFullName(commentUserResponse.getFullName());  // Gán username vào CommentResponse
            return commentResponse;
        }).toList();
        postResponse.setComments(commentResponses);  // Gán danh sách bình luận vào PostResponse
        postResponse.setFullName(userResponse.getFullName());  // Gán username vào PostResponse
        return postResponse;
    }


}

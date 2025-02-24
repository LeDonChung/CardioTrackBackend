package vn.edu.iuh.fit.post.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.post.client.UserServiceClient;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.jwt.JwtService;
import vn.edu.iuh.fit.post.mappers.PostMapper;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.entity.Post;
import vn.edu.iuh.fit.post.repositories.PostRepository;
import vn.edu.iuh.fit.post.services.PostService;
import vn.edu.iuh.fit.post.utils.SystemConstraints;

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

        Post post = postMapper.toEntity(postRequest);
        post.setCreatedAt(LocalDateTime.now()); // Set thời gian tạo bài viết
        post.setAuthorId(userId);  // Gán userId cho bài viết
        post.setComments(null);


        Post savedPost = postRepository.save(post);

        return postMapper.toResponse(savedPost);
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest, Long userId) throws PostException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(SystemConstraints.ACCESS_DENIED));

        if (!post.getAuthorId().equals(userId)) {
            throw new PostException("You are not the author of this post!");
        }
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        return postMapper.toResponse(updatedPost);
    }

    @Override
    public void deletePost(Long postId, Long userId) throws PostException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException("Post not found!"));

        if (!post.getAuthorId().equals(userId)) {
            throw new PostException("You are not the author of this post!");
        }

        postRepository.deleteById(postId);
    }

    @Override
    public List<PostResponse> searchPosts(String title) throws PostException {
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(title);
        if (posts.isEmpty()) {
            throw new PostException("No posts found with title: " + title);
        }
        return posts.stream()
                .map(postMapper::toResponse)
                .toList();
    }


}

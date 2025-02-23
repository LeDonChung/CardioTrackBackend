package vn.edu.iuh.fit.post.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.post.exceptions.PostException;
import vn.edu.iuh.fit.post.mappers.PostMapper;
import vn.edu.iuh.fit.post.model.dto.reponse.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.entity.Post;
import vn.edu.iuh.fit.post.repositories.PostRepository;
import vn.edu.iuh.fit.post.services.PostService;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

   @Autowired
   private PostRepository postRepository;
   @Autowired
    private PostMapper postMapper;
    @Override
    public PostResponse createPost(PostRequest postRequest) throws PostException {
        // Chuyển từ DTO sang Entity
        Post post = postMapper.toEntity(postRequest);
        post.setCreatedAt(LocalDateTime.now()); // Set thời gian tạo bài viết

        // Set id của người tạo bài viết cần lấy từ token - chưa làm dc
        post.setAuthorId(1L);

        post.setTitle(postRequest.getTitle());

        post.setContent(postRequest.getContent());

        //lúc này comment của bài viết chưa có nên set null
        post.setComments(null);



        // Lưu bài viết vào database
        Post savedPost = postRepository.save(post);

        // Chuyển từ Entity sang DTO để trả về client
        return postMapper.toResponse(savedPost);
    }
}

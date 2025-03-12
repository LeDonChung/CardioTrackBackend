package vn.edu.iuh.fit.post.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.post.model.entity.Comment;
import vn.edu.iuh.fit.post.model.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}

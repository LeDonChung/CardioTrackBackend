package vn.edu.iuh.fit.post.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.post.model.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //  Tìm kiếm bài viết có tiêu đề gần đúng
    List<Post> findByTitleContainingIgnoreCase(String title);

    List<Post> findByAuthorId(Long authorId);
}

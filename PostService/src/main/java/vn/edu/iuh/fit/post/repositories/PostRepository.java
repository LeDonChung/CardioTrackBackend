package vn.edu.iuh.fit.post.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.post.model.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}

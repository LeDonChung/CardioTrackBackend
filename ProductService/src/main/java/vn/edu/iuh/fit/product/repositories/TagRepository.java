package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.product.models.entities.Tag;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    Set<Tag> findByGroupId(Long groupId);
}

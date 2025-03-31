package vn.iuh.edu.fit.consult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iuh.edu.fit.consult.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

package vn.edu.iuh.fit.student.thanhtuyen.chatservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    @Query("{ 'role': { '$ne': 'consultant' } }")
    List<User> findAllUser();
}

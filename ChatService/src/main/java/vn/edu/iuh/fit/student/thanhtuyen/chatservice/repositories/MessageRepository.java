package vn.edu.iuh.fit.student.thanhtuyen.chatservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.Message;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Long> {

    @Query("{ '$or': [ { 'senderId': ?0 }, { 'receiverId': ?0 } ] }") //find by senderId or receiverId
    List<Message> findByUserId(Long userId);
}

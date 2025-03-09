package vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "messages")
public class Message {
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();
}

package vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "users")
public class User {
    @Id
    private Long id;
    private String username;
    private String role;
}

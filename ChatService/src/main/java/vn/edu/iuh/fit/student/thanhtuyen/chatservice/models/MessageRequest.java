package vn.edu.iuh.fit.student.thanhtuyen.chatservice.models;

import lombok.*;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.UserDto;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
public class MessageRequest {
    private UserDto sender;
    private UserDto receiver;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String imageUrl;
}

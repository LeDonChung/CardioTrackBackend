package vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDto {
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String imageUrl;
}

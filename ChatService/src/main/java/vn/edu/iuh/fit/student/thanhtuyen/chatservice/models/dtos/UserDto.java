package vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;
    private String username;
    private String role;
    private List<MessageDto> messages;
}

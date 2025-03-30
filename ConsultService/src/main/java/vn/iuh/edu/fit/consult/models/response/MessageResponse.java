package vn.iuh.edu.fit.consult.models.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
    private String role;
    private String content;
}

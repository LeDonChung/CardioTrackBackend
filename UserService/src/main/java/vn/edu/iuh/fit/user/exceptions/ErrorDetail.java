package vn.edu.iuh.fit.user.exceptions;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetail {
    private String error;
    private String message;
    private LocalDateTime timeStamp;
}

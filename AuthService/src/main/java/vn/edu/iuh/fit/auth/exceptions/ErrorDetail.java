package vn.edu.iuh.fit.auth.exceptions;

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

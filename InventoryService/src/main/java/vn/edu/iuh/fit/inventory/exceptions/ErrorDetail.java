package vn.edu.iuh.fit.inventory.exceptions;

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

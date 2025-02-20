package vn.edu.iuh.fit.user.exceptions;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class BaseException extends RuntimeException{
    private String code;
    private String message;
}

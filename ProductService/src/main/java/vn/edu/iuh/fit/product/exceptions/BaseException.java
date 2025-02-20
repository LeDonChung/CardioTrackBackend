package vn.edu.iuh.fit.product.exceptions;

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

package vn.edu.iuh.fit.healthcheck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.edu.iuh.fit.healthcheck.model.dto.response.BaseResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handlerResourceNotFound(ResourceNotFoundException ex) {
        BaseResponse<Object> baseResponse = BaseResponse
                .builder()
                .code(String.format(HttpStatus.NOT_FOUND.toString()))
                .data(ex.getLocalizedMessage())
                .success(false).build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HealthException.class)
    public ResponseEntity<ErrorDetail> handlerPostException(HealthException ex, WebRequest req) {
        ErrorDetail errorDetail = ErrorDetail.builder()
                .error(ex.getMessage())
                .message(req.getDescription(false))
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }


}
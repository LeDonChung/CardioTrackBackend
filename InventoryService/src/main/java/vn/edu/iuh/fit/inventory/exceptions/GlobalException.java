package vn.edu.iuh.fit.inventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;

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

//    @ExceptionHandler(CategoryException.class)
//    public ResponseEntity<ErrorDetail> handlerCategoryException(CategoryException ex, WebRequest req) {
//        ErrorDetail errorDetail = ErrorDetail.builder()
//                .error(ex.getMessage())
//                .message(req.getDescription(false))
//                .timeStamp(LocalDateTime.now())
//                .build();
//        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(MedicineException.class)
//    public ResponseEntity<ErrorDetail> handlerMedicineException(MedicineException ex, WebRequest req) {
//        ErrorDetail errorDetail = ErrorDetail.builder()
//                .error(ex.getMessage())
//                .message(req.getDescription(false))
//                .timeStamp(LocalDateTime.now())
//                .build();
//        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
//    }

}

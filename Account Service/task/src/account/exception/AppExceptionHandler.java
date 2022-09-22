package account.exception;

import account.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public void handleUserExistException(UserExistException ex,
                                         HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public void handleBindException(BindException ex,
                                    HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(createCustomError(ex.getBindingResult(), request, status));
    }

    private CustomError createCustomError(BindingResult ex, WebRequest request, HttpStatus status) {
        CustomError error = new CustomError();
        error.setStatus(status.value());
        error.setErrorMessage(status.getReasonPhrase());

        List<FieldError> fieldErrors = ex.getFieldErrors();
        StringBuilder message = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            message.append(fieldError.getDefaultMessage()).append(", ");
        }
        error.setMessage(message.substring(0, message.length() - 2));

        error.setPath(((ServletWebRequest)request).getRequest().getServletPath());

        return error;
    }

//    private CustomError createCustomError(BindingResult ex, WebRequest request, HttpStatus status) {
//        CustomError error = new CustomError();
//        error.setStatus(status.value());
//        error.setErrorMessage(status.getReasonPhrase());
//        error.setMessage(ex.getAllErrors().get(0).getDefaultMessage());
//
////        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
////        StringBuilder message = new StringBuilder();
////        for (FieldError fieldError : fieldErrors) {
////            message.append(fieldError.getDefaultMessage()).append(", ");
////        }
////        error.setMessage(message.substring(0, message.length() - 2));
//
//        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
//
//        return error;
//    }
}

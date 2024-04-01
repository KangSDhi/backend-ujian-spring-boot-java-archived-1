package dev.sigit.backendujianspringbootjava.advices;

import dev.sigit.backendujianspringbootjava.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse<Map<String, List<String>>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ){
        ErrorResponse<Map<String, List<String>>> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.BAD_REQUEST.value());
        Map<String, List<String>> body = new HashMap<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors){
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            body.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        }
        errorResponse.setErrors(body);
        logger.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse<String>> handleUsernameNotFound(UsernameNotFoundException ex){
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setErrors(ex.getMessage());
        logger.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse<String>> handleException(Exception ex){
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setErrors(ex.getMessage());
        logger.error(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}

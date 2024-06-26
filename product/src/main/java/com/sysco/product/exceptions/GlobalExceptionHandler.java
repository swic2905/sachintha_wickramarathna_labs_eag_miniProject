package com.sysco.product.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder().
                        error(HttpStatus.NOT_FOUND.value())
                        .message(exception.getMessage())
                        .build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder().
                        error(HttpStatus.BAD_REQUEST.value())
                        .message(errorMessages.toString())
                        .build());
    }

    @ExceptionHandler(value= ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception){
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorMessages.add(violation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder().
                        error(HttpStatus.BAD_REQUEST.value())
                        .message(errorMessages.toString())
                        .build());
    }


    @ExceptionHandler(value= MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                ErrorResponse.builder().
                        error(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(value= HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                ErrorResponse.builder().
                        error(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                ErrorResponse.builder().
                        error(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder().
                        error(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder().
                        error(HttpStatus.NOT_FOUND.value())
                        .message(exception.getRequestURL())
                        .build());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(
                ErrorResponse.builder().
                        error(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message(Objects.requireNonNull(exception.getContentType()).toString())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleCommonException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder().
                        error(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build());
    }
}

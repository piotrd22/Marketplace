package com.example.marketplace.config;

import com.example.marketplace.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        CustomError error = new CustomError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    protected ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest request) {
        CustomError error = new CustomError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    protected ResponseEntity<Object> handleEntityInUseException(EntityInUseException ex, WebRequest request) {
        CustomError error = new CustomError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InsufficientQuantityException.class)
    protected ResponseEntity<Object> handleInsufficientQuantityException(InsufficientQuantityException ex, WebRequest request) {
        CustomError error = new CustomError(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, ex.getMessage(), LocalDateTime.now());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleInvalidCartStateException(BadRequestException ex, WebRequest request) {
        CustomError error = new CustomError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private record CustomError(int statusCode, HttpStatus status, String message, LocalDateTime timestamp) { }
}

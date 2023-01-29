package com.user.management.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return new ResponseEntity<Map<String, Object>>(Map.of("message", ex.getMessage(), "status", HttpStatus.NOT_FOUND, "statusCode", 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, Object>> AlreadyExistsException(AlreadyExistsException ex) {
        return new ResponseEntity<Map<String, Object>>(Map.of("message", ex.getMessage(), "status", HttpStatus.CONFLICT, "statusCode", 409), HttpStatus.CONFLICT);
    }
}

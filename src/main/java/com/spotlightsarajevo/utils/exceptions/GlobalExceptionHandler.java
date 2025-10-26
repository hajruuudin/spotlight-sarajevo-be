package com.spotlightsarajevo.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AuthExceptions.UserRegisterException.class)
    public ResponseEntity<?> handleUserDataRegisterException(AuthExceptions.UserRegisterException ex){
        return buildResponse(HttpStatus.BAD_REQUEST, "User Data Registration Error", ex.getMessage());
    }

    @ExceptionHandler(AuthExceptions.GoogleLoginException.class)
    public ResponseEntity<?> handleGoogleLoginException(AuthExceptions.GoogleLoginException ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Google Login Error", ex.getMessage());
    }

    @ExceptionHandler(AuthExceptions.SystemLoginException.class)
    public ResponseEntity<?> handleSystemLoginException(AuthExceptions.SystemLoginException ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "System Login Error", ex.getMessage());
    }

    @ExceptionHandler(AuthExceptions.AuthenticationEndpointException.class)
    public ResponseEntity<?> handleAuthEndpointException(AuthExceptions.AuthenticationEndpointException ex){
        return buildResponse(HttpStatus.NOT_FOUND, "Authentication Endpoint Error", ex.getMessage());
    }

    /* General Exception handler for anything not specified */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage());
    }

    /* Build method used to create custom messages */
    private ResponseEntity<?> buildResponse(HttpStatus status, String error, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", error,
                "message", message
        ));
    }
}

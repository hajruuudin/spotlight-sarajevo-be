package com.spotlightsarajevo.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class SpotExceptions {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class SpotNotFoundException extends RuntimeException{
        public SpotNotFoundException(String message){
            super("ERROR - Spot with identifier not found: " + message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class SpotCreationException extends RuntimeException {
        public SpotCreationException(String message) {
            super("ERROR - Failed to create spot: " + message);
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class SpotUnauthorizedException extends RuntimeException {
        public SpotUnauthorizedException(String message) {
            super("ERROR - Unauthorized action on spot: " + message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class SpotConflictException extends RuntimeException {
        public SpotConflictException(String message) {
            super("ERROR - Spot conflict: " + message);
        }
    }
}

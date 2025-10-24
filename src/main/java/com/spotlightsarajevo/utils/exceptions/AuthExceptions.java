package com.spotlightsarajevo.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthExceptions {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class GoogleRegisterException extends RuntimeException {
        public GoogleRegisterException(String message){
            super("AUTH_ERROR: Google Register Token Error - " + message);
        }
    }
}

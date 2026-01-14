package com.spotlightsarajevo.common.exceptions;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MediaExceptions {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class MediaCreateException extends RuntimeException{
        public MediaCreateException(ExceptionCodes.MediaExceptionCodes message){
            super(message.toString());
        }
    }
}

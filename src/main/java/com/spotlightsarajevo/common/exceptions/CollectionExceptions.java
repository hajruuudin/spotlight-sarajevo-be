package com.spotlightsarajevo.common.exceptions;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CollectionExceptions {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class CollectionUnauthorisedException extends RuntimeException{
        public CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class CollectionRequestContentException extends RuntimeException{
        public CollectionRequestContentException(ExceptionCodes.CollectionExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class CollectionNotFoundException extends RuntimeException{
        public CollectionNotFoundException(ExceptionCodes.CollectionExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class CollectionItemNotFoundException extends RuntimeException{
        public CollectionItemNotFoundException(ExceptionCodes.CollectionExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static class CollectionSystemException extends RuntimeException{
        public CollectionSystemException(ExceptionCodes.CollectionExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class CollectionNamingException extends  RuntimeException{
        public CollectionNamingException(ExceptionCodes.CollectionExceptionCodes message) {
            super(message.toString());
        }
    }
}

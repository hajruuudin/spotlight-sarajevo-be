package com.spotlightsarajevo.common.exceptions;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TouristGuideExceptions {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class TouristGuideRequestException extends RuntimeException{
        public TouristGuideRequestException(ExceptionCodes.TouristGuideExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class TouristGuideNotFound extends RuntimeException{
        public TouristGuideNotFound(ExceptionCodes.TouristGuideExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class TouristGuideAuth extends RuntimeException{
        public TouristGuideAuth(ExceptionCodes.TouristGuideExceptionCodes message){
            super(message.toString());
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class TouristGuideConflict extends RuntimeException{
        public TouristGuideConflict(ExceptionCodes.TouristGuideExceptionCodes message){
            super(message.toString());
        }
    }
}

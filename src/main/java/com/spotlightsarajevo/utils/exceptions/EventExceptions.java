package com.spotlightsarajevo.utils.exceptions;

import com.spotlightsarajevo.service.implementation.EventServiceImpl;
import com.spotlightsarajevo.utils.enums.ExceptionCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EventExceptions {
    /**
     * Thrown when an error occurs during any method that attempts to retrieve Event information.
     *
     * <p>This exception is typically thrown from {@link EventServiceImpl}
     * when calling any method that attempts to find event based on certain criteria.</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class EventNotFoundException extends RuntimeException {
        public EventNotFoundException(String message){
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during any method that attempts persist new Event Objects.
     *
     * <p>This exception is typically thrown from {@link EventServiceImpl}
     * when an admin attempts to persist new events into the database.</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class EventCreationException extends RuntimeException {
        public EventCreationException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during any unauthorised exception in Event Service methods.
     *
     * <p>This exception is typically thrown from {@link EventServiceImpl}
     * when an unauthorised user attempts to modify, add or find data which he does not have permission for</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class EventUnauthorizedException extends RuntimeException {
        public EventUnauthorizedException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during event creation and updating conflicts.
     *
     * <p>This exception is typically thrown from {@link EventServiceImpl}
     * when an admin attempts to persist new events into the database without following unique value constraints.</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class EventConflictException extends RuntimeException {
        public EventConflictException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during any event operation which could contain invalid input formats.
     *
     * <p>This exception is typically thrown from {@link EventServiceImpl}
     * when providing parameters which do not conform to the required method specification (e.g. providing null params).</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class EventSystemException extends RuntimeException {
        public EventSystemException(ExceptionCodes.EventExceptionCodes message) {
            super(message.toString());
        }
    }
}

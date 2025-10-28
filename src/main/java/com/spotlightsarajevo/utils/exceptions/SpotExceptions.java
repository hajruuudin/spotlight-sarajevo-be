package com.spotlightsarajevo.utils.exceptions;

import com.spotlightsarajevo.service.implementation.SpotServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class SpotExceptions {
    /**
     * Thrown when an error occurs during any method that attempts to retrieve Spot information.
     *
     * <p>This exception is typically thrown from {@link SpotServiceImpl}
     * when calling any method that attempts to find spots based on certain criteria.</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class SpotNotFoundException extends RuntimeException {
        public SpotNotFoundException(String message){
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during any method that attempts persist new Spot Objects.
     *
     * <p>This exception is typically thrown from {@link SpotServiceImpl}
     * when an admin attempts to persist new spots into the database.</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class SpotCreationException extends RuntimeException {
        public SpotCreationException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during any unauthorised exception in Spot Service methods.
     *
     * <p>This exception is typically thrown from {@link SpotServiceImpl}
     * when an unauthorised user attempts to modify, add or find data which he does not have permission for</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class SpotUnauthorizedException extends RuntimeException {
        public SpotUnauthorizedException(String message) {
            super(message);
        }
    }

    /**
     * Thrown when an error occurs during spot creation and updating conflicts.
     *
     * <p>This exception is typically thrown from {@link SpotServiceImpl}
     * when an admin attempts to persist new spots into the database without following unique value constraints.</p>
     *
     * @see GlobalExceptionHandler
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    public static class SpotConflictException extends RuntimeException {
        public SpotConflictException(String message) {
            super(message);
        }
    }
}

package ba.spotlightsarajevo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Generic Response Wrapper class that contains information about the request response. It contains the message
 * of the response, the {@link ResponseCode}, the time of the response and the response payload.
 *
 * @param <T> The entity model response that was retrieved.
 */
@AllArgsConstructor
@Data
public class EntityResponse<T extends Serializable> {
    private String message;
    private String statusCode;
    private LocalDateTime time;
    private T payload;
}

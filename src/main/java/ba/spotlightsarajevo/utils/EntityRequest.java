package ba.spotlightsarajevo.utils;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Generic Entity Request wrapper class. Contains information regarding the user who made the request,
 * the time and date of the request and the payload of the request.
 *
 * @param <T> The entity model of the request
 */
@Data
public class EntityRequest<T extends Serializable> {
    private String user = "hajrudin.imamovic";
    private LocalDateTime requestTimestamp = LocalDateTime.now();
    private T data;

    public EntityRequest(T data){
        this.data = data;
    }
}
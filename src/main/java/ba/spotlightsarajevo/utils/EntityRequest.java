package ba.spotlightsarajevo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EntityRequest<T> {
    private String user = "hajrudin.imamovic";
    private LocalDateTime requestTimestamp = LocalDateTime.now();
    private T data;

    public EntityRequest(T data){
        this.data = data;
    }
}
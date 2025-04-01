package ba.spotlightsarajevo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class EntityResponse<T extends Serializable> {
    private String message;
    private String statusCode;
    private LocalDateTime time;
    private T payload;


}

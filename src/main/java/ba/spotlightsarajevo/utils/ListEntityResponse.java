package ba.spotlightsarajevo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class ListEntityResponse<T extends Serializable> {
    private String message;
    private String statusCode;
    private LocalDateTime time;
    private List<T> payload;
}
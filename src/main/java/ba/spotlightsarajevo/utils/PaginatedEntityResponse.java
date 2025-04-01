package ba.spotlightsarajevo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PaginatedEntityResponse<T extends Serializable> {
    private String message;
    private String statusCode;
    private LocalDateTime time;
    private int totalRecords;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private List<T> payload;

}

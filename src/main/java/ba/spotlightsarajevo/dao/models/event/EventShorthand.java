package ba.spotlightsarajevo.dao.models.event;

import ba.spotlightsarajevo.utils.ObjectShorthand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventShorthand implements Serializable, ObjectShorthand {
    @Serial
    private static final Long versionUUId = 1L;

    @Schema(description = "Unique identifier")
    private Integer id;

    @Schema(description = "URL-friendly identifier")
    private String slug;

    @Schema(description = "Official name")
    private String officialName;

    @Schema(description = "Short description")
    private String smallDescription;

    @Schema(description = "Start date of the event")
    private String startDateFormatted;

    @Schema(description = "Name of the category")
    private String categoryName;

    @Schema(description = "Tags names of the event")
    private List<String> tagNames;

    @Schema(description = "Date of creation of the event")
    private LocalDateTime created;

    @Schema(description = "Date of modification")
    private LocalDateTime modified;

    @Schema(description = "Image URL of the category")
    private String imageUrl;
}



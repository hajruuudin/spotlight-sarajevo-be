package ba.spotlightsarajevo.dao.models.mediastore;

import ba.spotlightsarajevo.enums.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Model for all properties of a media store entity")
public class MediaStoreModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the media object")
    private Integer id;

    @Schema(description = "Unique identifier of the object in question")
    private Integer itemId;

    @Schema(description = "Category of the object that needs the image (event, spot or system registered user)")
    private String itemCategory;

    @Schema(description = "Image url of the object")
    private String imageUrl;

    @Schema(description = "Thumbnail status")
    private Boolean isThumbnail;

    @Schema(description = "Date of creation of the object")
    private LocalDateTime created;

    @Schema(description = "Admin who added the image")
    private String createdBy;
}

package ba.spotlightsarajevo.dao.models.event;

import ba.spotlightsarajevo.dao.models.tag.TagUpdateModel;
import ba.spotlightsarajevo.utils.EventModificator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Properties for an Event create request")
public class EventCreate implements Serializable, EventModificator {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Official slug of the event to offer better search")
    private String slug;

    @Schema(description = "Official name of the event")
    private String officialName;

    @Schema(description = "Short description of the event")
    private String smallDescription;

    @Schema(description = "Full detailed description of the event")
    private String fullDescription;

    @Schema(description = "Name of the category this event belongs to")
    private String categoryName;

    @Schema(description = "List of tag names associated with the event")
    private List<TagUpdateModel> tags;

    @Schema(description = "Start date and time of the event (ISO format)")
    private String startDate;

    @Schema(description = "End date and time of the event (ISO format)")
    private String endDate;

    @Schema(description = "Ticket entry price for the event")
    private Integer entryPrice;

    @Schema(description = "Age restriction for the event")
    private Integer ageLimit;

    @Schema(description = "Whether the event allows reservations")
    private Boolean reservation;

    @Schema(description = "Whether the event is public or private")
    private String openStatus;

    @Schema(description = "Refund policy of the event")
    private String cancelRefund;

    @Schema(description = "Language in which the event will be held")
    private String eventLanguage;

    @Schema(description = "Venue or address where the event takes place")
    private String address;

    @Schema(description = "Additional location details")
    private String locationDescription;
}

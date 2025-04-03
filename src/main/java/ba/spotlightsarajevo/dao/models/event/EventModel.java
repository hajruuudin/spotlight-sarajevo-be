package ba.spotlightsarajevo.dao.models.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Model of all properties for an Event entity")
public class EventModel implements Serializable {
    @Serial
    private static final long versionUUId = 1L;

    @Schema(description = "Unique identifier of the event")
    private Integer id;

    @Schema(description = "Official slug of the event to offer better search")
    private String slug;

    @Schema(description = "Official name of the event (title)")
    private String officialName;

    @Schema(description = "Small description of the event (under the title)")
    private String smallDescription;

    @Schema(description = "Full description of the event")
    private String fullDescription;

    @Schema(description = "Category identifier of the event")
    private Integer categoryId;

    @Schema(description = "Start date and time of the event")
    private LocalDateTime startDate;

    @Schema(description = "End date and time of the event")
    private LocalDateTime endDate;

    @Schema(description = "Entry price of the event, if set")
    private BigDecimal entryPrice;

    @Schema(description = "Age limit of the event, if set")
    private Integer ageLimit;

    @Schema(description = "Reservation status requirement of the event")
    private Boolean reservation;

    @Schema(description = "The total refund of the event if a user cancels")
    private String cancelRefund;

    @Schema(description = "The open-status of the event, can be public, private, company related...")
    private String openStatus;

    @Schema(description = "Predominant language/s of the event")
    private String eventLanguage;

    @Schema(description = "Date of creation")
    private LocalDateTime created;

    @Schema(description = "Admin who created")
    private String createdBy;

    @Schema(description = "Date of modification")
    private LocalDateTime modified;

    @Schema(description = "Admin who modified")
    private String modifiedBy;
}

package com.spotlightsarajevo.dao.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Default, all property, table model for the Event Organiser Entity")
public class EventOrganiserModel implements Serializable {
    private static final Long versionUUID = 1L;

    @Schema(description = "Unique identifier of the event organiser")
    private Integer id;

    @Schema(description = "Name of the organiser")
    private String organiserName;

    @Schema(description = "Date when the organiser entity was created")
    private LocalDateTime organiserCreationDate;

    @Schema(description = "Category identifier of the organiser")
    private String organiserCategoryId;

    @Schema(description = "Phone number of the organiser")
    private String organiserPhone;

    @Schema(description = "Email address of the organiser")
    private String organiserEmail;

    @Schema(description = "Website URL of the organiser")
    private String organiserWebsite;
}

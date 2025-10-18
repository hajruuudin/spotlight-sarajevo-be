package com.spotlightsarajevo.dao.dto.transport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Default, all property, table model for the Transport Method Station Entity")
public class TransportMethodStationModel implements Serializable {
    private static final Long versionUUID = 1L;

    @Schema(description = "Unique identifier of the transport station")
    private Integer id;

    @Schema(description = "ID of the transport method type this station belongs to")
    private Integer transportTypeId;

    @Schema(description = "Line number of the transport route")
    private Integer lineNumber;

    @Schema(description = "Latitude coordinate of the station")
    private BigDecimal lineStationLat;

    @Schema(description = "Longitude coordinate of the station")
    private BigDecimal lineStationLong;

    @Schema(description = "Name of the station")
    private String stationName;
}


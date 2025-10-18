package com.spotlightsarajevo.dao.dto.communityrequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Default, all property, table model for the Community Request Entity")
public class CommunityRequestModel implements Serializable {
    private static final Long versionUUID = 1L;

    @Schema(description = "Unique identifier of the community request")
    private Integer id;

    @Schema(description = "Identifier of the user who made the request")
    private Integer userId;

    @Schema(description = "Full name of the user making the request")
    private String userFullName;

    @Schema(description = "Type of request submitted")
    private String requestType;

    @Schema(description = "Type of object the request concerns")
    private String objectType;

    @Schema(description = "Short header or title of the request")
    private String requestHeader;

    @Schema(description = "Detailed description of the request")
    private String requestDescription;
}


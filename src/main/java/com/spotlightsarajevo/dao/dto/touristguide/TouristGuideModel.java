package com.spotlightsarajevo.dao.dto.touristguide;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Default, all property, table model for the Tourist Guide Entity")
public class TouristGuideModel implements Serializable {
    private static final Long versionUUID = 1L;

    @Schema(description = "Unique identifier of the tourist guide")
    private Integer id;

    @Schema(description = "Title of the guide")
    private String guideTitle;

    @Schema(description = "Short description of the guide in Bosnian")
    private String guideSmallDescriptionBs;

    @Schema(description = "Short description of the guide in English")
    private String guideSmallDescriptionEn;
}

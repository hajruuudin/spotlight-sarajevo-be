package com.spotlightsarajevo.modules.guide.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Properties for a model to create an individual section for a tourist guide")
public class TouristGuideSectionCreateModel implements Serializable {
    @Schema(description = "Title of the section in Bosnian")
    private String sectionTitleBs;

    @Schema(description = "Title of the section in English")
    private String sectionTitleEn;

    @Schema(description = "Body/content of the section in Bosnian")
    private String sectionBodyBs;

    @Schema(description = "Body/content of the section in English")
    private String sectionBodyEn;

    @Schema(description = "Image of the section, labeled as thumbnail image")
    private String thumbnailImage;

    @Schema(description = "Order of the section inside the guide")
    private Integer orderIdx;
}

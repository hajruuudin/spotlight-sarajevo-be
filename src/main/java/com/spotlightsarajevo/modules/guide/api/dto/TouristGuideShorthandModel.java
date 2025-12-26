package com.spotlightsarajevo.modules.guide.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Schema(description = "Shorthand model for Tourist Guides, used for displaying preview cards in lists.")
public class TouristGuideShorthandModel implements Serializable {
    @Schema(description = "Unique identifier of the tourist guide", example = "1")
    private Long id;

    @Schema(description = "Slug of the tourist guide", example = "historija-sarajeva")
    private String slug;

    @Schema(description = "Main title of the guide in Bosnian", example = "Historija Sarajeva")
    private String titleBs;

    @Schema(description = "Main title of the guide in English", example = "History of Sarajevo")
    private String titleEn;

    @Schema(description = "Short summary/teaser of the guide in Bosnian", example = "Kratak pregled ključnih historijskih događaja...")
    private String smallDescriptionBs;

    @Schema(description = "Short summary/teaser of the guide in English", example = "A brief overview of key historical events...")
    private String smallDescriptionEn;

    @Schema(description = "URL of the thumbnail image representing the guide", example = "https://api.spotlightsarajevo.ba/media/guide_1_thumb.jpg")
    private String thumbnailUrl;

    @Schema(description = "The name of the assigned category in Bosnian", example = "Kultura")
    private String categoryNameBs;

    @Schema(description = "The name of the assigned category in English", example = "Culture")
    private String categoryNameEn;

    @Schema(description = "Image of the guide, labeled as thumbnail image")
    private String thumbnailImage;
}

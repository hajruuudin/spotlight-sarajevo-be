package com.spotlightsarajevo.modules.collection.api.dto;

import com.spotlightsarajevo.common.utils.CollectionItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "Properties for a model representing all items for a specific collection")
public class CollectionItemsModel implements Serializable {
    @Schema(description = "Identifier of the collection")
    private Integer collectionId;

    @Schema(description = "Type of the collection items")
    private String collectionType;

    @Schema(description = "List of items contained in the collection")
    private List<CollectionItem> collectionItems;
}

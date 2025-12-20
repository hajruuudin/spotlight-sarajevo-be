package com.spotlightsarajevo.modules.spot.facades;

import com.spotlightsarajevo.modules.spot.api.dto.SpotShorthandModel;

public interface SpotCollectionFacade {
    SpotShorthandModel getSpotShorthand(Integer spotId);
}

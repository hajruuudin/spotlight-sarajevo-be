package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.tag.TagModel;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;
import ba.spotlightsarajevo.utils.ListEntityResponse;

public interface TagService {
    ListEntityResponse<TagModel> findAll();
}

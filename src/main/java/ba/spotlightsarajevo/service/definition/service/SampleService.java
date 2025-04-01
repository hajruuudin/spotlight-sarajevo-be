package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.entities.SampleEntity;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;

public interface SampleService {
    public EntityResponse<SampleEntity> findById(EntityRequest<Long> request);
}

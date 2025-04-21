package ba.spotlightsarajevo.service.definition.service;

import ba.spotlightsarajevo.dao.models.tag.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<Page<TagModel>> findAll(PageRequest request);
}

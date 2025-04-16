package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.TagDAO;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
import ba.spotlightsarajevo.dao.entities.TagEntity;
import ba.spotlightsarajevo.dao.models.tag.TagModel;
import ba.spotlightsarajevo.service.definition.mapper.TagMapper;
import ba.spotlightsarajevo.service.definition.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    TagDAO tagDAO;
    TagMapper tagMapper;

    @Override
    public ResponseEntity<Page<TagModel>> findAll(PageRequest request) {
        Page<TagEntity> pagedTagResponse = tagDAO.findAll(request);

        List<TagModel> tagModelList = tagMapper.entitiesToDtos(pagedTagResponse.getContent());

        Page<TagModel> tagResponse = new PageImpl<>(
                tagModelList,
                request,
                pagedTagResponse.getTotalElements()
        );

        return ResponseEntity.ok(tagResponse);
    }
}

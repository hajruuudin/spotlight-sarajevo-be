package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.TagDAO;
import ba.spotlightsarajevo.dao.entities.TagEntity;
import ba.spotlightsarajevo.dao.models.tag.TagModel;
import ba.spotlightsarajevo.service.definition.mapper.TagMapper;
import ba.spotlightsarajevo.service.definition.service.TagService;
import ba.spotlightsarajevo.utils.EntityResponse;
import ba.spotlightsarajevo.utils.ListEntityResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    TagDAO tagDAO;
    TagMapper tagMapper;

    @Override
    public ListEntityResponse<TagModel> findAll() {
        List<TagEntity> tagEntities = tagDAO.findAll();

        List<TagModel> tagModels = tagMapper.entitiesToDtos(tagEntities);

        return new ListEntityResponse<>("Tags retrieved", "200", LocalDateTime.now(), tagModels);
    }
}

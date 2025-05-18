package ba.spotlightsarajevo.service.definition.mapper;

import ba.spotlightsarajevo.dao.entities.ReviewEntity;
import ba.spotlightsarajevo.dao.models.review.ReviewCreate;
import ba.spotlightsarajevo.dao.models.review.ReviewModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewModel entityToDto(ReviewEntity entity);
    ReviewEntity dtoToEntity(ReviewCreate reviewCreate);
    List<ReviewModel> etitiesToDtos(List<ReviewEntity> reviewEntities);

}

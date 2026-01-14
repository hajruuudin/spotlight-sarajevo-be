package com.spotlightsarajevo.modules.media.mapper;

import com.spotlightsarajevo.modules.media.api.dto.MediaStoreCreateModel;
import com.spotlightsarajevo.modules.media.api.dto.MediaStoreModel;
import com.spotlightsarajevo.modules.media.domain.entity.MediaStoreEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaStoreMapper {
    MediaStoreEntity dtoToEntity(MediaStoreCreateModel dto);
    MediaStoreModel entityToDto(MediaStoreEntity entity);
    List<MediaStoreEntity> entitiesToDtos(List<MediaStoreCreateModel> dtos);
}

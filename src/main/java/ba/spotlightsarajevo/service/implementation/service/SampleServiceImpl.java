package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.SampleDAO;
import ba.spotlightsarajevo.dao.entities.SampleEntity;
import ba.spotlightsarajevo.service.definition.service.SampleService;
import ba.spotlightsarajevo.utils.EntityRequest;
import ba.spotlightsarajevo.utils.EntityResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SampleServiceImpl implements SampleService {
    SampleDAO sampleDAO;

    @Override
    public EntityResponse<SampleEntity> findById(EntityRequest<Long> request) {
        Long entity = request.getData();
        SampleEntity sampleEntity = sampleDAO.findById(entity);

        return new EntityResponse<SampleEntity>(
                "Sample retrieved",
                "200 OK",
                LocalDateTime.now(),
                sampleEntity
        );
    }
}

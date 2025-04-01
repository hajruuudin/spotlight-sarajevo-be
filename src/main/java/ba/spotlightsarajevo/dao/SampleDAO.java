package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SampleEntity;
import ba.spotlightsarajevo.utils.AbstractDAO;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SampleDAO extends AbstractDAO<SampleEntity, Long> {
}

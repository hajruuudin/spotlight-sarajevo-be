package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionDAO extends JpaRepository<UserCollectionEntity, Integer> {
}

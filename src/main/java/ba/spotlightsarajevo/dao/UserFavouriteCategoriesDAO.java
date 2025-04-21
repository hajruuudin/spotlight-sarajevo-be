package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserFavouriteCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavouriteCategoriesDAO extends JpaRepository<UserFavouriteCategoriesEntity, Integer> {
}

package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.utils.AbstractDAO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractDAO<UserEntity, Integer> {
}

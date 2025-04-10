package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.utils.AbstractDAO;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO extends AbstractDAO<UserEntity, Integer> {
    @Transactional(readOnly = true)
    public UserEntity findByGoogleId(String googleId){
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.googleId = :googleId", UserEntity.class);
        query.setParameter("googleId", googleId);

        try {
            return query.getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public UserEntity findByEmail(String email) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.googleEmail = :email OR u.email = :email", UserEntity.class);
        query.setParameter("email", email);

        try {
            System.out.println("User gotten via:" + email + " is: " + query.getSingleResult());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public UserEntity findBySystemEmail(String email) {
        TypedQuery<UserEntity> query = entityManager.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
        query.setParameter("email", email);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

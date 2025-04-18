package ba.spotlightsarajevo.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract & Generic default DAO class that provides common database access methods for all
 * DAOs and Entities. Every DAO in the project extends this Abstract DAO. Any methods common to all
 * Database Objects should be placed here. Any methods custom to a specific Database Object should
 * be contained within the respective entity DAO implementation.
 *
 * @param <T> Entity Type
 * @param <K> Unique identifier in the entity table
 */
@Transactional
@Component
public abstract class AbstractDAO<T, K extends Serializable>{

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> genericType;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        Class<?>[] genericTypes = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractDAO.class);
        genericType = (Class<T>) genericTypes[0];
    }

    public T findById(K id) {
        try {
            T entity = entityManager.find(genericType, id);
            return entity;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public List<T> findAll() {
        try {
            TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + genericType.getSimpleName() + " e", genericType);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public T persist(T entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public T merge(T entity) {
        try {
            return entityManager.merge(entity);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void remove(T entity) {
        try {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeById(K id) {
        try {
            T entity = findById(id);
            if (entity != null) {
                remove(entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
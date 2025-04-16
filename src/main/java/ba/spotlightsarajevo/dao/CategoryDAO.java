package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.CategoryEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<CategoryEntity, Integer> {
    @NotNull Page<CategoryEntity> findAll(@NotNull Pageable request);
}

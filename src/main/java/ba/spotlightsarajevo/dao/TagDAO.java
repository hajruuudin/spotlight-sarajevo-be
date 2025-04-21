package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.TagEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDAO extends JpaRepository<TagEntity, Integer> {
    @NotNull Page<TagEntity> findAll(@NotNull Pageable request);
}

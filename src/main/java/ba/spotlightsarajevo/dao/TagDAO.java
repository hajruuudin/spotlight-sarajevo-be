package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.TagEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagDAO extends JpaRepository<TagEntity, Integer> {
    @NotNull Page<TagEntity> findAll(@NotNull Pageable request);
    Optional<TagEntity> findByTagName(String tagName);
}

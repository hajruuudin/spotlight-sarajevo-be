package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.SpotEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotDAO extends JpaRepository<SpotEntity, Integer> {
    @Query("SELECT se FROM SpotEntity se " +
            "JOIN SpotReviewStatsEntity sr ON sr.spotId = se.id " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR LOWER(se.officialName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND (:categoryIds IS NULL OR se.categoryId IN :categoryIds) " +
            "ORDER BY " +
            "CASE :sortOption " +
            "   WHEN 'rating' THEN sr.combinedRating " +
            "END DESC, " +
            "CASE :sortOption " +
            "   WHEN 'date' THEN se.created " +
            "END DESC, " +
            "CASE :sortOption " +
            "   WHEN 'alphabetical' THEN se.officialName " +
            "END ASC")
    Page<SpotEntity> findAll(Pageable pageable, @Param("searchTerm") String searchTerm, @Param("categoryIds") List<Integer> categoryIds, @Param("sortOption") String sortOption);

    Page<SpotEntity> findAllByCategoryId(Pageable request, Integer categoryId);

    SpotEntity findBySlug(String slug);

    @Query("SELECT se FROM SpotEntity se WHERE (:search IS NULL OR :search = '' OR LOWER(se.officialName) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    List<SpotEntity> findAllSearched(String search);
}

package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.EventEntity;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventDAO extends JpaRepository<EventEntity, Integer> {
    @Query("SELECT ee FROM EventEntity ee " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR LOWER(ee.officialName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND (:categoryIds IS NULL OR ee.categoryId IN :categoryIds) " +
            "ORDER BY " +
            "CASE :sortOption " +
            "   WHEN 'date' THEN ee.startDate " +
            "END ASC, " +
            "CASE :sortOption " +
            "   WHEN 'alphabetical' THEN ee.officialName " +
            "END ASC")
    Page<EventEntity> findAll(Pageable pageable, @Param("searchTerm") String searchTerm, @Param("categoryIds") List<Integer> categoryIds, @Param("sortOption") String sortOption);

    @Query("SELECT ee FROM EventEntity ee WHERE ee.startDate <= :endOfDate AND ee.endDate >= :startOfDate")
    Page<EventEntity> findByStartDate(Pageable pageable, LocalDateTime startOfDate, LocalDateTime endOfDate);

    EventEntity findBySlug(String slug);
}

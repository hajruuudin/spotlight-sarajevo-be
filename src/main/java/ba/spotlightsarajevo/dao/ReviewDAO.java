package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.ReviewEntity;
import ba.spotlightsarajevo.dao.entities.SpotEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDAO extends JpaRepository<ReviewEntity, Integer> {
    ReviewEntity findBySpotIdAndUserId(Integer spotId, Integer userId);

    @Query("SELECT re FROM ReviewEntity re WHERE spotId = :spotId AND userId != :userId")
    Page<ReviewEntity> findAllOthersBySpotId(Pageable request, Integer spotId, Integer userId);
}

package ba.spotlightsarajevo.dao;

import ba.spotlightsarajevo.dao.entities.MediaStoreEntity;
import ba.spotlightsarajevo.enums.ObjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaStoreDAO extends JpaRepository<MediaStoreEntity, Integer> {
    @Query("SELECT me FROM MediaStoreEntity me WHERE me.itemId = :itemId AND me.itemCategory = :itemCategory")
    List<MediaStoreEntity> findByItemIdAndItemCategory(@Param("itemId") Integer itemId, @Param("itemCategory") ObjectType itemCategory);

    @Query("SELECT me.imageUrl FROM MediaStoreEntity me WHERE me.itemId = :itemId AND me.itemCategory = :itemCategory AND me.isThumbnail = true")
    String findThumbnailUrlByItemIdAndItemCategory(@Param("itemId") Integer itemId, @Param("itemCategory") ObjectType itemCategory);

    @Query("SELECT me.imageUrl FROM MediaStoreEntity me WHERE me.itemId = :itemId AND me.itemCategory = :itemCategory AND me.isThumbnail = true ORDER BY me.created ASC LIMIT 1")
    String findFirstThumbnailUrlByItemIdAndItemCategoryOrderByCreatedAsc(@Param("itemId") Integer itemId, @Param("itemCategory") ObjectType itemCategory);

    // Retrieve all image URLs for a specific item and category
    @Query("SELECT me.imageUrl FROM MediaStoreEntity me WHERE me.itemId = :itemId AND me.itemCategory = :itemCategory")
    List<String> findAllImageUrlsByItemIdAndItemCategory(@Param("itemId") Integer itemId, @Param("itemCategory") ObjectType itemCategory);

    // Retrieve all thumbnail image URLs for a specific item and category
    @Query("SELECT me.imageUrl FROM MediaStoreEntity me WHERE me.itemId = :itemId AND me.itemCategory = :itemCategory AND me.isThumbnail = true")
    List<String> findAllThumbnailUrlsByItemIdAndItemCategory(@Param("itemId") Integer itemId, @Param("itemCategory") ObjectType itemCategory);
}

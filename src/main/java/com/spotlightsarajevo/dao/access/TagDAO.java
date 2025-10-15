package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDAO extends JpaRepository<TagEntity, Integer> {
}

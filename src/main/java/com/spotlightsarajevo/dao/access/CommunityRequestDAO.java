package com.spotlightsarajevo.dao.access;

import com.spotlightsarajevo.dao.entities.CommunityRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRequestDAO extends JpaRepository<CommunityRequestEntity, Integer> {
}

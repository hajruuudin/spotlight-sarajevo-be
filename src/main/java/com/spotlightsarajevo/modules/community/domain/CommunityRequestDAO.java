package com.spotlightsarajevo.modules.community.domain;

import com.spotlightsarajevo.modules.community.domain.entity.CommunityRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRequestDAO extends JpaRepository<CommunityRequestEntity, Integer> {
}

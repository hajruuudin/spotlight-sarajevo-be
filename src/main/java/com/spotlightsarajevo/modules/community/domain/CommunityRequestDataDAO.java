package com.spotlightsarajevo.modules.community.domain;

import com.spotlightsarajevo.modules.community.domain.entity.CommunityRequestDataEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRequestDataDAO extends JpaRepository<CommunityRequestDataEntity, Integer> {
}

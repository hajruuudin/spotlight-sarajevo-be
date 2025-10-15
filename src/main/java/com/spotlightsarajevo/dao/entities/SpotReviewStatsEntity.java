package com.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_spot_review_stats") // DATABASE VIEW REFERENCE, SHOULD NOT BE UPDATABLE / EXTENDED
public class SpotReviewStatsEntity implements Serializable {
    @Id
    @Column(name = "spot_id")
    private Integer spotId;

    @Column(name = "combined_rating", columnDefinition = "NUMERIC")
    private BigDecimal combinedRating;

    @Column(name = "combined_cleanliness", columnDefinition = "NUMERIC")
    private BigDecimal combinedCleanliness;

    @Column(name = "combined_affordability", columnDefinition = "NUMERIC")
    private BigDecimal combinedAffordability;

    @Column(name = "combined_accessibility", columnDefinition = "NUMERIC")
    private BigDecimal combinedAccessibility;

    @Column(name = "combined_staff_kindness", columnDefinition = "NUMERIC")
    private BigDecimal combinedStaffKindness;

    @Column(name = "combined_quality", columnDefinition = "NUMERIC")
    private BigDecimal combinedQuality;

    @Column(name = "combined_atmosphere", columnDefinition = "NUMERIC")
    private BigDecimal combinedAtmosphere;
}

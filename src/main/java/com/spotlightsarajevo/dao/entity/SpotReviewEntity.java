package com.spotlightsarajevo.dao.entity;

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
@Table(name = "ss_spot_review")
public class SpotReviewEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "header", columnDefinition = "VARCHAR", length = 255)
    private String header;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "user_overall_rating")
    private Integer userOverallRating;

    @Column(name = "user_cleanliness", columnDefinition = "NUMERIC")
    private BigDecimal userCleanliness;

    @Column(name = "user_affordability", columnDefinition = "NUMERIC")
    private BigDecimal userAffordability;

    @Column(name = "user_accessibility", columnDefinition = "NUMERIC")
    private BigDecimal userAccessibility;

    @Column(name = "user_staff_kindness", columnDefinition = "NUMERIC")
    private BigDecimal userStaffKindness;

    @Column(name = "user_locale_quality", columnDefinition = "NUMERIC")
    private BigDecimal userLocaleQuality;

    @Column(name = "user_atmosphere", columnDefinition = "NUMERIC")
    private BigDecimal userAtmosphere;

    @Column(name = "spot_id")
    private Integer spotId;
}

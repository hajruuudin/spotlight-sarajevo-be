package com.spotlightsarajevo.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_spot")
public class SpotEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "official_name", columnDefinition = "VARCHAR", length = 40)
    private String officialName;

    @Column(name = "small_description_bs", columnDefinition = "VARCHAR", length = 60)
    private String smallDescriptionBs;

    @Column(name = "small_description_en", columnDefinition = "VARCHAR", length = 60)
    private String smallDescriptionEn;

    @Column(name = "full_description_bs", columnDefinition = "TEXT")
    private String fullDescriptionBs;

    @Column(name = "full_description_en", columnDefinition = "TEXT")
    private String fullDescriptionEn;

    @Column(name = "latitude", columnDefinition = "NUMERIC")
    private BigDecimal latitude;

    @Column(name = "longitude", columnDefinition = "NUMERIC")
    private BigDecimal longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "initial_overall_rating", columnDefinition = "NUMERIC")
    private BigDecimal initialOverallRating;

    @Column(name = "initial_cleanliness", columnDefinition = "NUMERIC")
    private BigDecimal initialCleanliness;

    @Column(name = "initial_affordability", columnDefinition = "NUMERIC")
    private BigDecimal initialAffordability;

    @Column(name = "initial_accessibility", columnDefinition = "NUMERIC")
    private BigDecimal initialAccessibility;

    @Column(name = "initial_staff_kindness", columnDefinition = "NUMERIC")
    private BigDecimal initialStaffKindness;

    @Column(name = "initial_locale_quality", columnDefinition = "NUMERIC")
    private BigDecimal initialLocaleQuality;

    @Column(name = "initial_atmosphere", columnDefinition = "NUMERIC")
    private BigDecimal initialAtmosphere;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;
}

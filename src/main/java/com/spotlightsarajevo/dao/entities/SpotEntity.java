package com.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
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

    @Column(name = "official_name")
    private String officialName;

    @Column(name = "small_description_bs")
    private String smallDescriptionBs;

    @Column(name = "small_description_en")
    private String smallDescriptionEn;

    @Column(name = "full_description_bs")
    private String fullDescriptionBs;

    @Column(name = "full_description_en")
    private String fullDescriptionEn;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "initial_overall_rating")
    private BigDecimal initialOverallRating;

    @Column(name = "initial_cleanliness")
    private BigDecimal initialCleanliness;

    @Column(name = "initial_affordability")
    private BigDecimal initialAffordability;

    @Column(name = "initial_accessibility")
    private BigDecimal initialAccessibility;

    @Column(name = "initial_staff_kindness")
    private BigDecimal initialStaffKindness;

    @Column(name = "initial_locale_quality")
    private BigDecimal initialLocaleQuality;

    @Column(name = "initial_atmosphere")
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

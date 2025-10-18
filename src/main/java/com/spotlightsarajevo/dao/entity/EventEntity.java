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
@Table(name = "ss_event")
public class EventEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "slug", columnDefinition = "VARCHAR", length = 255, nullable = false, unique = true)
    private String slug;

    @Column(name = "official_name", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String officialName;

    @Column(name = "small_description_bs", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String smallDescriptionBs;

    @Column(name = "small_description_en", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String smallDescriptionEn;

    @Column(name = "full_description_bs", columnDefinition = "TEXT", nullable = false)
    private String fullDescriptionBs;

    @Column(name = "full_description_en", columnDefinition = "TEXT", nullable = false)
    private String fullDescriptionEn;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "entry_price", columnDefinition = "NUMERIC")
    private BigDecimal entryPrice;

    @Column(name = "age_limit", columnDefinition = "NUMERIC")
    private BigDecimal ageLimit;

    @Column(name = "reservation")
    private Boolean reservation;

    @Column(name = "cancel_refund", columnDefinition = "VARCHAR", length = 255)
    private String cancelRefund;

    @Column(name = "event_language", columnDefinition = "VARCHAR", length = 255)
    private String eventLanguage;

    @Column(name = "event_lat", columnDefinition = "NUMERIC", nullable = false)
    private BigDecimal eventLat;

    @Column(name = "event_long", columnDefinition = "NUMERIC", nullable = false)
    private BigDecimal eventLong;

    @Column(name = "location", columnDefinition = "VARCHAR", nullable = false)
    private String location;

    @Column(name = "organiser_id", nullable = false)
    private Integer organiserId;
}
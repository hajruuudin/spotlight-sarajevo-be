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
@Table(name = "ss_event_review")
public class EventReviewEntity implements Serializable {
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

    @Column(name = "user_event_quality", columnDefinition = "NUMERIC")
    private BigDecimal userEventQuality;

    @Column(name = "user_event_enjoyability", columnDefinition = "NUMERIC")
    private BigDecimal userEventEnjoyability;

    @Column(name = "user_event_atmosphere", columnDefinition = "NUMERIC")
    private BigDecimal userEventAtmosphere;

    @Column(name = "event_id")
    private Integer eventId;
}

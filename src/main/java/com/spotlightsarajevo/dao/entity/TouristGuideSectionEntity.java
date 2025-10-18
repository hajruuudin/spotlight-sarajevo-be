package com.spotlightsarajevo.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_tourist_guide_section")
public class TouristGuideSectionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "guide_id", nullable = false)
    private Integer guideId;

    @Column(name = "section_title_bs", columnDefinition = "VARCHAR", length = 50, nullable = false)
    private String sectionTitleBs;

    @Column(name = "section_title_en", columnDefinition = "VARCHAR", length = 50, nullable = false)
    private String sectionTitleEn;

    @Column(name = "section_body_bs", columnDefinition = "TEXT", nullable = false)
    private String sectionBodyBs;

    @Column(name = "section_body_en", columnDefinition = "TEXT", nullable = false)
    private String sectionBodyEn;
}

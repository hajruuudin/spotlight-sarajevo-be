package com.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_tourist_guide")
public class TouristGuideEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "guide_title", columnDefinition = "VARCHAR", length = 30, nullable = false)
    private String guideTitle;

    @Column(name = "guide_small_description_bs", columnDefinition = "VARCHAR", length = 50, nullable = false)
    private String guideSmallDescriptionBs;

    @Column(name = "guide_small_description_en", columnDefinition = "VARCHAR", length = 50, nullable = false)
    private String guideSmallDescriptionEn;
}

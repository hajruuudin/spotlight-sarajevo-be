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
@Table(name = "ss_transport_method")
public class TransportMethodEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "method_name_bs", columnDefinition = "VARCHAR", length = 30, nullable = false)
    private String methodNameBs;

    @Column(name = "method_name_en", columnDefinition = "VARCHAR", length = 30, nullable = false)
    private String methodNameEn;

    @Column(name = "method_description_bs", columnDefinition = "TEXT", nullable = false)
    private String methodDescriptionBs;

    @Column(name = "method_description_en", columnDefinition = "TEXT", nullable = false)
    private String methodDescriptionEn;
}

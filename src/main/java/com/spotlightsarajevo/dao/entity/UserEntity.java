package com.spotlightsarajevo.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "locale", columnDefinition = "VARCHAR", length = 10)
    private String locale;

    @Column(name = "first_name", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String lastName;

    @Column(name = "is_premium")
    private Boolean isPremium;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "category_01")
    private Integer category01;

    @Column(name = "category_02")
    private Integer category02;

    @Column(name = "category_03")
    private Integer category03;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
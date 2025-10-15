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
@Table(name = "ss_community_request")
public class CommunityRequestEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "user_full_name", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String userFullName;

    @Column(name = "request_type", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String requestType;

    @Column(name = "object_type", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String objectType;

    @Column(name = "request_header", columnDefinition = "VARCHAR", length = 30, nullable = false)
    private String requestHeader;

    @Column(name = "request_description", columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String requestDescription;
}

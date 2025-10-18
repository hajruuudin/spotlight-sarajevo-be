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
@Table(name = "ss_collection_event")
public class CollectionEventEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "collection_id", nullable = false)
    private Integer collectionId;

    @Column(name = "event_id", nullable = false)
    private Integer eventId;
}

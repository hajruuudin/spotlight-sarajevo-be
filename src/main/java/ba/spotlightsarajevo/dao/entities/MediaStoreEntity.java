package ba.spotlightsarajevo.dao.entities;

import ba.spotlightsarajevo.enums.ObjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_media_store")
public class MediaStoreEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "item_id")
    private Integer itemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_category")
    private ObjectType itemCategory;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;
}

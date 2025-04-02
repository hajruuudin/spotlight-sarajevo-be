package ba.spotlightsarajevo.dao.entities;

import ba.spotlightsarajevo.enums.ObjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_collection")
public class CollectionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "collection_name")
    private String collection_name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "collection_type")
    private ObjectType collection_type;

}

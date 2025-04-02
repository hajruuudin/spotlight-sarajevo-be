package ba.spotlightsarajevo.dao.entities;

import ba.spotlightsarajevo.enums.CommunityRequestType;
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
@Table(name = "ss_community_request")
public class CommunityRequestEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "user_name")
    private String user_name;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private CommunityRequestType request_type;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private ObjectType object_type;

    @Column(name = "request_name")
    private String request_name;

    @Column(name = "request_description")
    private String request_description;
}

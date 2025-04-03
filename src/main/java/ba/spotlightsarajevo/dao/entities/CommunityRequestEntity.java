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
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private CommunityRequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private ObjectType objectType;

    @Column(name = "request_name")
    private String requestName;

    @Column(name = "request_description")
    private String requestDescription;
}

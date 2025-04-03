package ba.spotlightsarajevo.dao.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_spot")
public class SpotEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "official_name")
    private String officialName;

    @Column(name = "small_description")
    private String smallDescription;

    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "lattitude")
    private BigDecimal lattitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "cleanliness")
    private BigDecimal cleanliness;

    @Column(name = "affordability")
    private BigDecimal affordability;

    @Column(name = "accessibility")
    private BigDecimal accessibility;

    @Column(name = "staff_kindness")
    private BigDecimal staffKindness;

    @Column(name = "quality")
    private BigDecimal quality;

    @Column(name = "atmosphere")
    private BigDecimal atmosphere;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;
}
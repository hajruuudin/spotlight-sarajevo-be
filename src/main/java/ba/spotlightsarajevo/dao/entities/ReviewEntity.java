package ba.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_review")
public class ReviewEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "header")
    private String header;

    @Column(name = "body")
    private String body;

    @Column(name = "rating")
    private Integer rating;

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

    @Column(name = "spot_id")
    private Integer spotId;
}

package ba.spotlightsarajevo.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.View;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_spot_review_stats")
public class SpotReviewStatsEntity implements Serializable {

    @Id
    @Column(name = "spot_id")
    private Integer spotId;

    @Column(name = "combined_cleanliness")
    private BigDecimal combinedCleanliness;

    @Column(name = "combined_affordability")
    private BigDecimal combinedAffordability;

    @Column(name = "combined_accessibility")
    private BigDecimal combinedAccessibility;

    @Column(name = "combined_staff_kindness")
    private BigDecimal combinedStaffKindness;

    @Column(name = "combined_quality")
    private BigDecimal combinedQuality;

    @Column(name = "combined_atmosphere")
    private BigDecimal combinedAtmosphere;
}

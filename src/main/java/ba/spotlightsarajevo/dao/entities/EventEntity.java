package ba.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ss_event")
public class EventEntity implements Serializable {

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

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "entry_price")
    private BigDecimal entryPrice;

    @Column(name = "age_limit")
    private Integer ageLimit;

    @Column(name = "reservation")
    private Boolean reservation;

    @Column(name = "cancel_refund")
    private String cancelRefund;

    @Column(name = "open_status")
    private String openStatus;

    @Column(name = "event_language")
    private String eventLanguage;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modifiedBy;



}

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
    private String official_name;

    @Column(name = "small_description")
    private String small_description;

    @Column(name = "full_description")
    private String full_description;

    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "start_date")
    private LocalDateTime start_date;

    @Column(name = "end_date")
    private LocalDateTime end_date;

    @Column(name = "entry_price")
    private BigDecimal entry_price;

    @Column(name = "age_limit")
    private Integer age_limit;

    @Column(name = "reservation")
    private Boolean reservation;

    @Column(name = "cancel_refund")
    private String cancel_refund;

    @Column(name = "open_status")
    private String open_satus;

    @Column(name = "event_language")
    private String event_language;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "created_by")
    private String created_by;

    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "modified_by")
    private String modified_by;



}

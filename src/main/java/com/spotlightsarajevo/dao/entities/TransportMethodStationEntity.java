package com.spotlightsarajevo.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_transport_method_station")
public class TransportMethodStationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "transport_type_id", nullable = false)
    private Integer transportTypeId;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "line_station_lat", columnDefinition = "NUMERIC")
    private BigDecimal lineStationLat;

    @Column(name = "line_station_long", columnDefinition = "NUMERIC")
    private BigDecimal lineStationLong;

    @Column(name = "station_name", columnDefinition = "VARCHAR", length = 255)
    private String stationName;
}

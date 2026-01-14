package com.spotlightsarajevo.modules.transport.service;

import com.spotlightsarajevo.modules.transport.api.dto.TransportMethodLineModel;
import com.spotlightsarajevo.modules.transport.api.dto.TransportMethodModel;
import com.spotlightsarajevo.modules.transport.api.dto.TransportMethodShorthandModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransportService {
    ResponseEntity<List<TransportMethodShorthandModel>> findAllTransportMethods();
    ResponseEntity<TransportMethodModel> findTransportMethodById(Integer id);
    ResponseEntity<List<TransportMethodLineModel>> findLinesByOperatorAndTransportType(Integer operatorId, Integer transportTypeId);
}


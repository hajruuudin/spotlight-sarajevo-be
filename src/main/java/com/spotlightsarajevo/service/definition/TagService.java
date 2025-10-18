package com.spotlightsarajevo.service.definition;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    ResponseEntity<List<TagService>> findAll();
}

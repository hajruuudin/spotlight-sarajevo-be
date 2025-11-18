package com.spotlightsarajevo.modules.tag.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    ResponseEntity<List<TagService>> findAll();
}

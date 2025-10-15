package com.spotlightsarajevo.service.implementation;

import com.spotlightsarajevo.service.definition.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Override
    public ResponseEntity<List<TagService>> findAll() {
        return null;
    }
}

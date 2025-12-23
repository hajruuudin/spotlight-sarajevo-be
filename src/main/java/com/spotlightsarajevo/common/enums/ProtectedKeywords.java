package com.spotlightsarajevo.common.enums;

import lombok.Getter;

@Getter
public enum ProtectedKeywords {
    ALL_SPOTS("All Spots"),
    ALL_EVENTS("All Events");

    private final String value;

    ProtectedKeywords(String value) {
        this.value = value;
    }
}

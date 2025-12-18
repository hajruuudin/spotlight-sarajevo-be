package com.spotlightsarajevo.common.enums;

public class ExceptionCodes {
    public enum SpotExceptionCodes {
        INVALID_REQUEST_CONTENT,
        SPOT_NOT_FOUND,
        EMPTY_SPOT_REQUEST_BODY,
        SPOT_UNAUTHORIZED_ACCESS,
        SPOT_UPDATE_EXCEPTION,
        SPOT_REVIEW_NOT_FOUND,
        SPOT_REVIEW_CONFLICT
    }

    public enum EventExceptionCodes {
        INVALID_REQUEST_CONTENT,
        EVENT_NOT_FOUND,
        EVENT_UNAUTHORIZED_ACCESS,
        EVENT_UPDATE_EXCEPTION,
        EVENT_ORGANISER_REVIEW_NOT_FOUND
    }

    public enum CollectionExceptionCodes {
        COLLECTION_FIND_UNAUTHORISED,
        COLLECTION_REQUEST_INVALID,
        COLLECTION_NOT_FOUND,
        COLLECTION_UNAUTHORISED_ACCESS,
        COLLECTION_INVALID_TYPE,
        COLLECTION_ITEM_NOT_FOUND,
        COLLECTION_SYSTEM_EXCEPTION
    }
}

package com.spotlightsarajevo.modules.collection.utils;

import com.spotlightsarajevo.common.enums.ExceptionCodes;
import com.spotlightsarajevo.common.exceptions.CollectionExceptions;
import com.spotlightsarajevo.modules.collection.domain.CollectionDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CollectionUtils {
    public boolean checkAuthentication(Integer collectionUserId, Integer userId){
        if(!Objects.equals(collectionUserId, userId)){
            throw new CollectionExceptions.CollectionUnauthorisedException(ExceptionCodes.CollectionExceptionCodes.COLLECTION_UNAUTHORISED_ACCESS);
        } else {
            return true;
        }
    }
}

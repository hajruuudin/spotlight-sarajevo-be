package com.spotlightsarajevo.common.specifications;

import com.spotlightsarajevo.modules.event.domain.entity.EventEntity;
import com.spotlightsarajevo.common.enums.SortingOptions;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EventSpecification {
    public static Specification<EventEntity> hasSearchTerms(String searchTerm){
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.toString().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + searchTerm.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("officialNameEn")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("officialNameBs")), pattern)
            );
        };
    }

    public static Specification<EventEntity> hasCategories(List<Integer> categoryIds){
        return (root, query, criteriaBuilder) -> {
            if(categoryIds.isEmpty()){
                return criteriaBuilder.conjunction();
            }

            return root.get("categoryId").in(categoryIds);
        };
    }

    public static Specification<EventEntity> withDynamicSorting(String sortOption){
        return (root, query, crieriaBuilder) -> {

            SortingOptions sort;

            try {
                sort = SortingOptions.valueOf(sortOption.toUpperCase());
            } catch (IllegalArgumentException e){
                sort = SortingOptions.ALPHABETICAL;
            }

            switch (sort) {
                case ALPHABETICAL -> query.orderBy(crieriaBuilder.asc(root.get("officialNameEn")));
                case DATE -> query.orderBy(crieriaBuilder.asc(root.get("startDate")));
            };

            return crieriaBuilder.conjunction();
        };
    }
}

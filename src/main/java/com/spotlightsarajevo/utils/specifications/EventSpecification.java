package com.spotlightsarajevo.utils.specifications;

import com.spotlightsarajevo.dao.entity.EventEntity;
import com.spotlightsarajevo.utils.enums.SortingOptions;
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
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("officialName")), pattern)
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
                case ALPHABETICAL -> query.orderBy(crieriaBuilder.asc(root.get("officialName")));
                case DATE -> query.orderBy(crieriaBuilder.asc(root.get("startDate")));
            };

            return crieriaBuilder.conjunction();
        };
    }
}

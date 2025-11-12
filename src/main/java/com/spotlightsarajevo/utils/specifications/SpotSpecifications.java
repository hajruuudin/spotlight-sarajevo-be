package com.spotlightsarajevo.utils.specifications;

import com.spotlightsarajevo.dao.entity.SpotEntity;
import com.spotlightsarajevo.utils.enums.SortingOptions;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpotSpecifications {
    public static Specification<SpotEntity> hasSearchTerm(String searchTerm){
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

    public static Specification<SpotEntity> hasCategories(List<Integer> categoryIds){
        return (root, query, criteriaBuilder) -> {
            if (categoryIds.isEmpty()){
                return criteriaBuilder.conjunction();
            }

            return root.get("categoryId").in(categoryIds);
        };
    }

    public static Specification<SpotEntity> withDynamicSorting(String sortOption) {
        return (root, query, criteriaBuilder) -> {

            SortingOptions sorting;
            try {
                sorting = SortingOptions.valueOf(sortOption.toUpperCase());
            } catch (IllegalArgumentException e) {
                sorting = SortingOptions.ALPHABETICAL;
            }

            switch (sorting) {
                case ALPHABETICAL -> query.orderBy(criteriaBuilder.asc(root.get("officialName")));
                case RATING -> {
                    Join<Object, Object> reviewStatsJoin = root.join("reviewStats", JoinType.LEFT);
                    query.orderBy(criteriaBuilder.desc(reviewStatsJoin.get("combinedRating")));
                }
            }

            return criteriaBuilder.conjunction();
        };
    }

}

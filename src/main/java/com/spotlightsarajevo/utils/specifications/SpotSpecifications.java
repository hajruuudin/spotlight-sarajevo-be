package com.spotlightsarajevo.utils.specifications;

import com.spotlightsarajevo.dao.entity.SpotEntity;
import com.spotlightsarajevo.utils.enums.SortingOptions;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpotSpecifications {
    public static Specification<SpotEntity> hasSearchTerm(String searchTerm){
        return (root, query, criteriaBuilder) -> {
            if (searchTerm == null || searchTerm.toString().isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + searchTerm + "%";

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

}

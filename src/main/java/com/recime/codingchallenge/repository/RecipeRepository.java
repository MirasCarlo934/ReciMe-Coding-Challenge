package com.recime.codingchallenge.repository;

import com.recime.codingchallenge.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    @Query(value = QueryConstants.RECIPE_SEARCH_QUERY,
           countQuery = QueryConstants.RECIPE_SEARCH_COUNT_QUERY,
           nativeQuery = true)
    Page<Recipe> findRecipesWithCriteria(
            @Param("servings") Integer servings,
            @Param("includeIngredients") String includeIngredients,
            @Param("excludeIngredients") String excludeIngredients,
            @Param("includeInstructions") String includeInstructions,
            @Param("excludeInstructions") String excludeInstructions,
            Pageable pageable
    );
}

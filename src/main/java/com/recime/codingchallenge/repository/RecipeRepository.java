package com.recime.codingchallenge.repository;

import com.recime.codingchallenge.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    @Query(value = "SELECT DISTINCT r.* FROM recipe r " +
                  "WHERE (:servings IS NULL OR r.servings = :servings) " +
                  "AND (COALESCE(array_length(string_to_array(:includeIngredients, ','), 1), 0) = 0 OR " +
                  "     array_length(string_to_array(:includeIngredients, ','), 1) = (" +
                  "         SELECT COUNT(DISTINCT pattern) FROM unnest(string_to_array(:includeIngredients, ',')) AS pattern " +
                  "         WHERE EXISTS (" +
                  "             SELECT 1 FROM recipe_ingredients ri " +
                  "             WHERE ri.recipe_id = r.id AND ri.name ~ pattern" +
                  "         )" +
                  "     )) " +
                  "AND (COALESCE(array_length(string_to_array(:excludeIngredients, ','), 1), 0) = 0 OR " +
                  "     NOT EXISTS (" +
                  "         SELECT 1 FROM recipe_ingredients ri, " +
                  "                unnest(string_to_array(:excludeIngredients, ',')) AS pattern " +
                  "         WHERE ri.recipe_id = r.id AND ri.name ~ pattern" +
                  "     ))",
           nativeQuery = true)
    List<Recipe> findRecipesWithCriteria(
            @Param("servings") Integer servings,
            @Param("includeIngredients") String includeIngredients,
            @Param("excludeIngredients") String excludeIngredients
    );
}

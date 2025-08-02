package com.recime.codingchallenge.repository;

import com.recime.codingchallenge.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    @Query(value = """
            SELECT DISTINCT r.* FROM recipe r
            WHERE (:servings IS NULL OR r.servings = :servings)
            AND (:includeIngredients IS NULL OR
                 cardinality(string_to_array(:includeIngredients, ',')) = (
                     SELECT COUNT(*) FROM unnest(string_to_array(:includeIngredients, ',')) AS ingredient
                     WHERE EXISTS (SELECT 1 FROM recipe_ingredients ri
                                   WHERE ri.recipe_id = r.id AND UPPER(ri.name) = UPPER(ingredient))))
            AND (:excludeIngredients IS NULL OR
                 NOT EXISTS (SELECT 1 FROM unnest(string_to_array(:excludeIngredients, ',')) AS ingredient
                             WHERE EXISTS (SELECT 1 FROM recipe_ingredients ri
                                          WHERE ri.recipe_id = r.id AND UPPER(ri.name) = UPPER(ingredient))))
            AND (:includeInstructions IS NULL OR
                 cardinality(string_to_array(:includeInstructions, ',')) = (
                     SELECT COUNT(*) FROM unnest(string_to_array(:includeInstructions, ',')) AS pattern
                     WHERE EXISTS (SELECT 1 FROM recipe_instructions ri
                                   WHERE ri.recipe_id = r.id AND ri.instruction ~* pattern)))
            AND (:excludeInstructions IS NULL OR
                 NOT EXISTS (SELECT 1 FROM unnest(string_to_array(:excludeInstructions, ',')) AS pattern
                             WHERE EXISTS (SELECT 1 FROM recipe_instructions ri
                                          WHERE ri.recipe_id = r.id AND ri.instruction ~* pattern)))
            """,
           nativeQuery = true)
    List<Recipe> findRecipesWithCriteria(
            @Param("servings") Integer servings,
            @Param("includeIngredients") String includeIngredients,
            @Param("excludeIngredients") String excludeIngredients,
            @Param("includeInstructions") String includeInstructions,
            @Param("excludeInstructions") String excludeInstructions
    );
}

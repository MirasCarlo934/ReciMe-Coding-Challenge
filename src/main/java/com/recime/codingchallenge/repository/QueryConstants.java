package com.recime.codingchallenge.repository;

public class QueryConstants {
    private static final String PRIVATE_SEARCH_WHERE_CLAUSE = """
            WHERE (:servings IS NULL OR r.servings = :servings)
            AND (:includeIngredients IS NULL OR
                 cardinality(string_to_array(:includeIngredients, ',')) = (
                     SELECT COUNT(*) FROM unnest(string_to_array(:includeIngredients, ',')) AS ingredient
                     WHERE EXISTS (SELECT 1 FROM recipe_ingredients ri
                                   WHERE ri.recipe_id = r.id AND ri.name = ingredient)))
            AND (:excludeIngredients IS NULL OR
                 NOT EXISTS (SELECT 1 FROM unnest(string_to_array(:excludeIngredients, ',')) AS ingredient
                             WHERE EXISTS (SELECT 1 FROM recipe_ingredients ri
                                          WHERE ri.recipe_id = r.id AND ri.name = ingredient)))
            AND (:includeInstructions IS NULL OR
                 cardinality(string_to_array(:includeInstructions, ',')) = (
                     SELECT COUNT(*) FROM unnest(string_to_array(:includeInstructions, ',')) AS pattern
                     WHERE EXISTS (SELECT 1 FROM recipe_instructions ri
                                   WHERE ri.recipe_id = r.id AND ri.instruction ~* pattern)))
            AND (:excludeInstructions IS NULL OR
                 NOT EXISTS (SELECT 1 FROM unnest(string_to_array(:excludeInstructions, ',')) AS pattern
                             WHERE EXISTS (SELECT 1 FROM recipe_instructions ri
                                          WHERE ri.recipe_id = r.id AND ri.instruction ~* pattern)))
            """;

    static final String RECIPE_SEARCH_QUERY = """
            SELECT DISTINCT r.* FROM recipe r
            """ + PRIVATE_SEARCH_WHERE_CLAUSE;

    static final String RECIPE_SEARCH_COUNT_QUERY = """
            SELECT COUNT(DISTINCT r.id) FROM recipe r
            """ + PRIVATE_SEARCH_WHERE_CLAUSE;
}

package com.recime.codingchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchCriteria {
    private Boolean vegetarian;
    private Integer servings;
    private List<String> includeIngredients;  // now regex strings - ALL must be satisfied
    private List<String> excludeIngredients;  // now regex strings - NONE must be satisfied
    private List<String> includeInstructions; // now regex strings - ALL must be satisfied
    private List<String> excludeInstructions; // now regex strings - NONE must be satisfied

    /**
     * Checks if all search criteria are empty/null, indicating no filtering is needed.
     * @return true if no search criteria are specified
     */
    public boolean isEmpty() {
        return vegetarian == null &&
               servings == null &&
               (includeIngredients == null || includeIngredients.isEmpty()) &&
               (excludeIngredients == null || excludeIngredients.isEmpty()) &&
               (includeInstructions == null || includeInstructions.isEmpty()) &&
               (excludeInstructions == null || excludeInstructions.isEmpty());
    }
}

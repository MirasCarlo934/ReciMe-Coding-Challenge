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
    private List<String> includeIngredients;
    private List<String> excludeIngredients;
    private List<String> includeInstructions;
    private List<String> excludeInstructions;

    /**
     * Checks if all search criteria are empty/null, indicating no filtering is needed.
     *
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

package com.recime.codingchallenge.dto;

import com.recime.codingchallenge.model.Instruction;
import com.recime.codingchallenge.model.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO used to represent Recipe entity in API response body.
 */
@Data
@Builder
public class RecipeDto {
    private String id;
    private String title;
    private String description;
    private List<IngredientDto> ingredients;
    private List<String> instructions;
    private int servings;
    private boolean vegetarian;

    public static RecipeDto from(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients().stream()
                        .map(IngredientDto::from)
                        .collect(Collectors.toList()))
                .instructions(recipe.getInstructions().stream()
                        .map(Instruction::getInstruction)
                        .collect(Collectors.toUnmodifiableList()))
                .servings(recipe.getServings())
                .vegetarian(recipe.isVegetarian())
                .build();
    }
}

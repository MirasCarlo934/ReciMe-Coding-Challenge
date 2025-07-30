package com.recime.codingchallenge.model;

import com.recime.codingchallenge.dto.RecipeDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @ElementCollection
    private List<Ingredient> ingredients;
    @ElementCollection
    private List<String> instructions;
    private int servings;

    public boolean isVegetarian() {
        return ingredients.stream().allMatch(Ingredient::isVegetarian);
    }

    /**
     * Updates the current recipe with the values from another recipe.
     * This method only updates the fields that are non-null in the given recipe.
     *
     * @param recipe the recipe containing the new values
     */
    // TODO: It might be more relevant to have a RecipeDto for this operation
    public void updateWithNonNullFields(Recipe recipe) {
        if (recipe.title != null) {
            this.title = recipe.title;
        }
        if (recipe.description != null) {
            this.description = recipe.description;
        }
        if (recipe.ingredients != null) {
            this.ingredients = recipe.ingredients;
        }
        if (recipe.instructions != null) {
            this.instructions = recipe.instructions;
        }
    }

    public static Recipe from(RecipeDto recipeDto) {
        return Recipe.builder()
            .title(recipeDto.getTitle())
            .description(recipeDto.getDescription())
            .ingredients(recipeDto.getIngredients().stream()
                    .map(Ingredient::from)
                    .collect(Collectors.toList()))
            .instructions(recipeDto.getInstructions())
            .build();
    }
}

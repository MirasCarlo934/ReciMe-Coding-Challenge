package com.recime.codingchallenge.model;

import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // needed for JPA
@AllArgsConstructor // needed for @Builder
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
     * @param recipeDto the recipe containing the new values
     */
    public void updateWithNonNullFields(UpdateRecipeDto recipeDto) {
        if (recipeDto.getTitle() != null) {
            this.title = recipeDto.getTitle();
        }
        if (recipeDto.getDescription() != null) {
            this.description = recipeDto.getDescription();
        }
        if (recipeDto.getIngredients() != null && !recipeDto.getIngredients().isEmpty()) {
            this.ingredients = recipeDto.getIngredients().stream()
                .map(Ingredient::from)
                .collect(Collectors.toList());
        }
        if (recipeDto.getInstructions() != null && !recipeDto.getInstructions().isEmpty()) {
            this.instructions = recipeDto.getInstructions();
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
                .servings(recipeDto.getServings())
                .build();
    }
}

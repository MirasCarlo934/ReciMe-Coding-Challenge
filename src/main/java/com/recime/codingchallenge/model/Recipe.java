package com.recime.codingchallenge.model;

import com.recime.codingchallenge.dto.CreateRecipeDto;
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

    /**
     * Checks if the recipe is vegetarian.
     * A recipe is considered vegetarian if all its ingredients are vegetarian.
     *
     * @return true if all ingredients are vegetarian, false otherwise
     */
    public boolean isVegetarian() {
        return ingredients.stream().allMatch(Ingredient::isVegetarian);
    }

    /**
     * Updates the current recipe with the values from another recipe.
     * This method only updates the fields that are non-null in the given recipe.
     *
     * @param updateRecipeDto the DTO containing the new values
     */
    public void updateWithNonNullFields(UpdateRecipeDto updateRecipeDto) {
        if (updateRecipeDto.getTitle() != null) {
            this.title = updateRecipeDto.getTitle();
        }
        if (updateRecipeDto.getDescription() != null) {
            this.description = updateRecipeDto.getDescription();
        }
        if (updateRecipeDto.getIngredients() != null && !updateRecipeDto.getIngredients().isEmpty()) {
            this.ingredients = updateRecipeDto.getIngredients().stream()
                .map(Ingredient::from)
                .collect(Collectors.toList());
        }
        if (updateRecipeDto.getInstructions() != null && !updateRecipeDto.getInstructions().isEmpty()) {
            this.instructions = updateRecipeDto.getInstructions();
        }
    }

    /**
     * Creates a Recipe instance from a CreateRecipeDto.
     *
     * @param createRecipeDto the DTO containing recipe data
     * @return a new Recipe instance
     */
    public static Recipe from(CreateRecipeDto createRecipeDto) {
        return Recipe.builder()
                .title(createRecipeDto.getTitle())
                .description(createRecipeDto.getDescription())
                .ingredients(createRecipeDto.getIngredients().stream()
                        .map(Ingredient::from)
                        .collect(Collectors.toList()))
                .instructions(createRecipeDto.getInstructions())
                .servings(createRecipeDto.getServings())
                .build();
    }
}

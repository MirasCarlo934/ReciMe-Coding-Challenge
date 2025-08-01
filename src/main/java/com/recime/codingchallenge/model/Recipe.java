package com.recime.codingchallenge.model;

import com.recime.codingchallenge.dto.RecipeRequestDto;
import com.recime.codingchallenge.dto.RecipeUpdateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // needed for JPA
@AllArgsConstructor // needed for @Builder
public class Recipe {
    public static final List<String> ALLOWED_SORT_FIELDS = List.of("title", "servings", "description");

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @ElementCollection
    private List<Ingredient> ingredients;
    @ElementCollection
    private List<Instruction> instructions;
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
     * Returns the list of instructions sorted by their order.
     * This method ensures that the instructions are returned in the correct sequence.
     *
     * @return a sorted list of instructions
     */
    public List<Instruction> getInstructions() {
        return instructions.stream()
                .sorted(Comparator.comparingInt(Instruction::getStep))
                .collect(Collectors.toList());
    }

    /**
     * Updates the current recipe with the values from another recipe.
     * This method only updates the fields that are non-null in the given recipe.
     *
     * @param recipeUpdateRequestDto the DTO containing the new values
     */
    public void updateWithNonNullFields(RecipeUpdateRequestDto recipeUpdateRequestDto) {
        if (recipeUpdateRequestDto.getTitle() != null) {
            this.title = recipeUpdateRequestDto.getTitle();
        }
        if (recipeUpdateRequestDto.getDescription() != null) {
            this.description = recipeUpdateRequestDto.getDescription();
        }
        if (recipeUpdateRequestDto.getIngredients() != null && !recipeUpdateRequestDto.getIngredients().isEmpty()) {
            this.ingredients = recipeUpdateRequestDto.getIngredients().stream()
                .map(Ingredient::from)
                .collect(Collectors.toList());
        }
        if (recipeUpdateRequestDto.getInstructions() != null && !recipeUpdateRequestDto.getInstructions().isEmpty()) {
            this.instructions = buildInstrucionsList(recipeUpdateRequestDto.getInstructions());
        }
    }

    /**
     * Creates a Recipe instance from a CreateRecipeDto.
     *
     * @param recipeRequestDto the DTO containing recipe data
     * @return a new Recipe instance
     */
    public static Recipe from(RecipeRequestDto recipeRequestDto) {
        return Recipe.builder()
                .title(recipeRequestDto.getTitle())
                .description(recipeRequestDto.getDescription())
                .ingredients(recipeRequestDto.getIngredients().stream()
                        .map(Ingredient::from)
                        .collect(Collectors.toList()))
                .instructions(buildInstrucionsList(recipeRequestDto.getInstructions()))
                .servings(recipeRequestDto.getServings())
                .build();
    }

    /**
     * Builds a list of Instruction objects from a list of instruction strings.
     * Each instruction string is assigned an order based on its position in the list.
     *
     * @param instructions the list of instruction strings
     * @return a list of Instruction objects
     */
    private static List<Instruction> buildInstrucionsList(List<String> instructions) {
        List<Instruction> instructionList = new ArrayList<>();
        for (int i = 0; i < instructions.size(); i++) {
            instructionList.add(new Instruction(instructions.get(i), i));
        }
        return instructionList;
    }
}

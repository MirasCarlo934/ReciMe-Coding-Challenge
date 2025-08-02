package com.recime.codingchallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * DTO used to represent a Recipe update request body.
 * This DTO is used when updating a recipe via the API. Notice that this does not have all the validations as in
 * RecipeRequestDto, as it is possible to only update specific fields.
 */
@Data
public class RecipeUpdateRequestDto {
    private String title;
    private String description;
    private List<@Valid IngredientDto> ingredients;
    private List<@NotBlank(message = "Instruction cannot be blank") String> instructions;
    private int servings;
}

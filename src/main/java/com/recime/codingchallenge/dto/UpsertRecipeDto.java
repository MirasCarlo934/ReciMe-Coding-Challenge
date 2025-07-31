package com.recime.codingchallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class UpsertRecipeDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotEmpty(message = "Ingredients cannot be empty")
    private List<@Valid IngredientDto> ingredients;
    @NotEmpty(message = "Instructions cannot be empty")
    private List<@NotBlank(message = "Instruction cannot be blank") String> instructions;
    @Positive(message = "Servings must be a positive integer")
    private int servings;
}

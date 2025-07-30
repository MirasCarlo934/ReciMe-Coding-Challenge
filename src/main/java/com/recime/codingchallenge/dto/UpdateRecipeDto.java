package com.recime.codingchallenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRecipeDto {
    private String title;
    private String description;
    private List<@Valid IngredientDto> ingredients;
    private List<@NotBlank(message = "Instruction cannot be blank") String> instructions;
    private int servings;
}

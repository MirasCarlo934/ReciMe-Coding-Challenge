package com.recime.codingchallenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IngredientDto {
    @Positive(message = "Amount must be a positive number")
    private float amount;
    private String unit; // unit can be null or empty, indicating count or "pcs" (e.g. "2 dried bay leaves")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Vegetarian status cannot be null")
    private Boolean vegetarian; // using Boolean to catch null/missing value
}

package com.recime.codingchallenge.dto;

import com.recime.codingchallenge.model.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientDto {
    @Positive(message = "Amount must be a positive number")
    private float amount;
    private String unit; // unit can be null or empty, indicating count or "pcs" (e.g. "2 dried bay leaves")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Vegetarian status cannot be null")
    private Boolean vegetarian; // using Boolean to catch null/missing value

    public static IngredientDto from(Ingredient ingredient) {
        return new IngredientDto(
                ingredient.getAmount(),
                ingredient.getUnit(),
                ingredient.getName(),
                ingredient.isVegetarian()
        );
    }
}

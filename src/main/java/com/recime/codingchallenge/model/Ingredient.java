package com.recime.codingchallenge.model;

import com.recime.codingchallenge.dto.IngredientDto;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // needed for JPA
@AllArgsConstructor // needed for @Builder
public class Ingredient {
    private float amount;
    private String unit;
    private String name;
    private boolean vegetarian;

    public static Ingredient from(IngredientDto ingredientDto) {
        return Ingredient.builder()
            .amount(ingredientDto.getAmount())
            .unit(ingredientDto.getUnit())
            .name(ingredientDto.getName())
            .vegetarian(ingredientDto.getVegetarian())
            .build();
    }
}

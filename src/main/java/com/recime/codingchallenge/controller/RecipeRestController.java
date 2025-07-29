package com.recime.codingchallenge.controller;

import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.service.RecipeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(@Qualifier("recipeService") RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipes")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }
}

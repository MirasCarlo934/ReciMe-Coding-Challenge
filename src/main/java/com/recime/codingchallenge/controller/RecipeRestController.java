package com.recime.codingchallenge.controller;

import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.service.RecipeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(@Qualifier("recipeService") RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @DeleteMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }
}

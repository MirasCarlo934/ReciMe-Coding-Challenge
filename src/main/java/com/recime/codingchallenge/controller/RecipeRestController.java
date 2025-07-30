package com.recime.codingchallenge.controller;

import com.recime.codingchallenge.dto.CreateRecipeDto;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(@Qualifier("recipeService") RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    @ResponseStatus(HttpStatus.OK)
    public List<Recipe> getRecipes(
            @RequestParam(value = "vegetarian", required = false) Boolean vegetarian,
            @RequestParam(value = "servings", required = false) Integer servings,
            @RequestParam(value = "includeIngredients", required = false) List<String> includeIngredients,
            @RequestParam(value = "excludeIngredients", required = false) List<String> excludeIngredients,
            @RequestParam(value = "instructions", required = false) List<String> instructions
    ) {
        if (vegetarian == null && servings == null &&
            (includeIngredients == null || includeIngredients.isEmpty()) &&
            (excludeIngredients == null || excludeIngredients.isEmpty()) &&
            (instructions == null || instructions.isEmpty())) {
            return recipeService.getAllRecipes();
        }
        return recipeService.searchRecipes(vegetarian, servings, includeIngredients, excludeIngredients, instructions);
    }

    @GetMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe getRecipeById(@PathVariable String id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe createRecipe(@Valid @RequestBody CreateRecipeDto createRecipeDto) {
        return recipeService.createRecipe(createRecipeDto);
    }

    @PatchMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recipe updateRecipe(@PathVariable String id, @Valid @RequestBody UpdateRecipeDto updateRecipeDto) {
        return recipeService.updateRecipe(id, updateRecipeDto);
    }

    @DeleteMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }
}

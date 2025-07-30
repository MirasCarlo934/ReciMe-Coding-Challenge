package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(String id);
    Recipe createRecipe(RecipeDto recipeDto);
    Recipe updateRecipe(String id, Recipe recipe);
    void deleteRecipe(String id);
}

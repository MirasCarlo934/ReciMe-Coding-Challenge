package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.CreateRecipeDto;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import com.recime.codingchallenge.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe getRecipeById(String id);
    Recipe createRecipe(CreateRecipeDto createRecipeDto);
    Recipe updateRecipe(String id, UpdateRecipeDto updateRecipeDto);
    void deleteRecipe(String id);
}

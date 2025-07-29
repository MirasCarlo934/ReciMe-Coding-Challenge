package com.recime.codingchallenge.service;

import com.recime.codingchallenge.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
    Recipe createRecipe(Recipe recipe);
    void deleteRecipe(String id);
}

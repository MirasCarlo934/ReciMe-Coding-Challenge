package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.CreateRecipeDto;
import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.dto.RecipeSearchCriteria;
import com.recime.codingchallenge.dto.UpdateRecipeDto;

import java.util.List;

public interface RecipeService {
    List<RecipeDto> getAllRecipes();
    RecipeDto getRecipeById(String id);
    RecipeDto createRecipe(CreateRecipeDto createRecipeDto);
    RecipeDto updateRecipe(String id, UpdateRecipeDto updateRecipeDto);
    void deleteRecipe(String id);
    List<RecipeDto> searchRecipes(RecipeSearchCriteria searchCriteria);
}

package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.UpsertRecipeDto;
import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.dto.RecipeSearchCriteria;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    Page<RecipeDto> getAllRecipes(Pageable pageable);
    RecipeDto getRecipeById(String id);
    RecipeDto createRecipe(UpsertRecipeDto upsertRecipeDto);
    RecipeDto updateRecipe(String id, UpdateRecipeDto updateRecipeDto);
    void deleteRecipe(String id);
    Page<RecipeDto> searchRecipes(RecipeSearchCriteria searchCriteria, Pageable pageable);
}

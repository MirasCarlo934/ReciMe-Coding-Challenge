package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.RecipeRequestDto;
import com.recime.codingchallenge.dto.RecipeResponseDto;
import com.recime.codingchallenge.dto.RecipeSearchRequestDto;
import com.recime.codingchallenge.dto.RecipeUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
    Page<RecipeResponseDto> getAllRecipes(Pageable pageable);
    RecipeResponseDto getRecipeById(String id);
    RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto);
    RecipeResponseDto updateRecipe(String id, RecipeUpdateRequestDto recipeUpdateRequestDto);
    RecipeResponseDto replaceRecipe(String id, RecipeRequestDto recipeRequestDto);
    void deleteRecipe(String id);
    Page<RecipeResponseDto> searchRecipes(RecipeSearchRequestDto recipeSearchRequestDto, Pageable pageable);
}

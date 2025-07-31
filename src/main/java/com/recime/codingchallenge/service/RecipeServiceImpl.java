package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.UpsertRecipeDto;
import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.dto.RecipeSearchCriteria;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import com.recime.codingchallenge.exception.RecipeNotFoundException;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("recipeService")
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        log.info("Fetching all recipes from the repository with pagination: {}", pageable);
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        return recipePage.map(RecipeDto::from);
    }

    @Override
    public RecipeDto getRecipeById(String id) {
        log.info("Fetching recipe with ID: {}", id);
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return RecipeDto.from(recipe);
    }

    @Override
    public RecipeDto createRecipe(UpsertRecipeDto upsertRecipeDto) {
        log.info("Saving recipe: {}", upsertRecipeDto);
        Recipe savedRecipe = recipeRepository.save(Recipe.from(upsertRecipeDto));
        return RecipeDto.from(savedRecipe);
    }

    @Override
    public RecipeDto updateRecipe(String id, UpdateRecipeDto updateRecipeDto) {
        log.info("Updating recipe with ID: {}", id);
        Recipe origRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        origRecipe.updateWithNonNullFields(updateRecipeDto);
        Recipe updatedRecipe = recipeRepository.save(origRecipe);
        return RecipeDto.from(updatedRecipe);
    }

    @Override
    public void deleteRecipe(String id) {
        log.info("Deleting recipe with ID: {}", id);
        recipeRepository.deleteById(id);
    }

    @Override
    // TODO: Fix this method to be simpler
    public Page<RecipeDto> searchRecipes(RecipeSearchCriteria searchCriteria, Pageable pageable) {
        log.info("Searching recipes with criteria: {} and pagination: {}", searchCriteria, pageable);

        // Convert ingredient and instruction lists to comma-separated strings for the native query
        String includeIngredientsStr = searchCriteria.getIncludeIngredients() != null && !searchCriteria.getIncludeIngredients().isEmpty()
                ? String.join(",", searchCriteria.getIncludeIngredients()) : null;
        String excludeIngredientsStr = searchCriteria.getExcludeIngredients() != null && !searchCriteria.getExcludeIngredients().isEmpty()
                ? String.join(",", searchCriteria.getExcludeIngredients()) : null;
        String includeInstructionsStr = searchCriteria.getIncludeInstructions() != null && !searchCriteria.getIncludeInstructions().isEmpty()
                ? String.join(",", searchCriteria.getIncludeInstructions()) : null;
        String excludeInstructionsStr = searchCriteria.getExcludeInstructions() != null && !searchCriteria.getExcludeInstructions().isEmpty()
                ? String.join(",", searchCriteria.getExcludeInstructions()) : null;

        // Use the paginated repository method
        Page<Recipe> recipePage = recipeRepository.findRecipesWithCriteria(
                searchCriteria.getServings(),
                includeIngredientsStr,
                excludeIngredientsStr,
                includeInstructionsStr,
                excludeInstructionsStr,
                pageable
        );

        // Apply vegetarian filter and convert to DTOs
        if (searchCriteria.getVegetarian() != null) {
            List<RecipeDto> filteredRecipes = recipePage.getContent().stream()
                    .filter(r -> r.isVegetarian() == searchCriteria.getVegetarian())
                    .map(RecipeDto::from)
                    .collect(Collectors.toList());

            // Create a new page with filtered content
            return new PageImpl<>(filteredRecipes, pageable, filteredRecipes.size());
        }

        // No vegetarian filter needed, just convert to DTOs
        return recipePage.map(RecipeDto::from);
    }
}

package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.CreateRecipeDto;
import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.dto.RecipeSearchCriteria;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import com.recime.codingchallenge.exception.RecipeNotFoundException;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
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
    public List<RecipeDto> getAllRecipes() {
        log.info("Fetching all recipes from the repository");
        return recipeRepository.findAll().stream()
                .map(RecipeDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDto getRecipeById(String id) {
        log.info("Fetching recipe with ID: {}", id);
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return RecipeDto.from(recipe);
    }

    @Override
    public RecipeDto createRecipe(CreateRecipeDto createRecipeDto) {
        log.info("Saving recipe: {}", createRecipeDto);
        Recipe savedRecipe = recipeRepository.save(Recipe.from(createRecipeDto));
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
    public List<RecipeDto> searchRecipes(RecipeSearchCriteria searchCriteria) {
        log.info("Searching recipes with criteria: {}", searchCriteria);

        // Convert ingredient and instruction lists to comma-separated strings for the native query
        String includeIngredientsStr = searchCriteria.getIncludeIngredients() != null && !searchCriteria.getIncludeIngredients().isEmpty()
                ? String.join(",", searchCriteria.getIncludeIngredients()) : null;
        String excludeIngredientsStr = searchCriteria.getExcludeIngredients() != null && !searchCriteria.getExcludeIngredients().isEmpty()
                ? String.join(",", searchCriteria.getExcludeIngredients()) : null;
        String includeInstructionsStr = searchCriteria.getIncludeInstructions() != null && !searchCriteria.getIncludeInstructions().isEmpty()
                ? String.join(",", searchCriteria.getIncludeInstructions()) : null;
        String excludeInstructionsStr = searchCriteria.getExcludeInstructions() != null && !searchCriteria.getExcludeInstructions().isEmpty()
                ? String.join(",", searchCriteria.getExcludeInstructions()) : null;

        // Apply repository-level filters (servings, ingredients, and instructions) using native SQL
        List<Recipe> recipes = recipeRepository.findRecipesWithCriteria(
                searchCriteria.getServings(),
                includeIngredientsStr,
                excludeIngredientsStr,
                includeInstructionsStr,
                excludeInstructionsStr
        );

        // Apply remaining filters at application level and convert to DTOs
        return recipes.stream()
                .filter(r -> searchCriteria.getVegetarian() == null || r.isVegetarian() == searchCriteria.getVegetarian())
                .map(RecipeDto::from)
                .collect(Collectors.toList());
    }
}

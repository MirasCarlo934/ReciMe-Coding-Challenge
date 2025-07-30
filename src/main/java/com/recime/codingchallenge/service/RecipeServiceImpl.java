package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.CreateRecipeDto;
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
    public List<Recipe> getAllRecipes() {
        log.info("Fetching all recipes from the repository");
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(String id) {
        log.info("Fetching recipe with ID: {}", id);
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public Recipe createRecipe(CreateRecipeDto createRecipeDto) {
        log.info("Saving recipe: {}", createRecipeDto);
        return recipeRepository.save(Recipe.from(createRecipeDto));
    }

    @Override
    public Recipe updateRecipe(String id, UpdateRecipeDto updateRecipeDto) {
        log.info("Updating recipe with ID: {}", id);
        Recipe origRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        origRecipe.updateWithNonNullFields(updateRecipeDto);
        return recipeRepository.save(origRecipe);
    }

    @Override
    public void deleteRecipe(String id) {
        log.info("Deleting recipe with ID: {}", id);
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> searchRecipes(RecipeSearchCriteria searchCriteria) {
        log.info("Searching recipes with criteria: {}", searchCriteria);

        // Apply repository-level filter (servings only) using JPQL
        List<Recipe> recipes = recipeRepository.findRecipesWithCriteria(searchCriteria.getServings());

        // Apply all other filters at application level using regex
        return recipes.stream()
                .filter(r -> searchCriteria.getVegetarian() == null || r.isVegetarian() == searchCriteria.getVegetarian())
                .filter(r -> searchCriteria.getIncludeIngredients() == null || searchCriteria.getIncludeIngredients().isEmpty() ||
                        (r.getIngredients() != null && searchCriteria.getIncludeIngredients().stream().allMatch(regexPattern ->
                                r.getIngredients().stream().anyMatch(ing -> ing.getName().matches(regexPattern)))))
                .filter(r -> searchCriteria.getExcludeIngredients() == null || searchCriteria.getExcludeIngredients().isEmpty() ||
                        (r.getIngredients() != null && searchCriteria.getExcludeIngredients().stream().noneMatch(regexPattern ->
                                r.getIngredients().stream().anyMatch(ing -> ing.getName().matches(regexPattern)))))
                .filter(r -> searchCriteria.getIncludeInstructions() == null || searchCriteria.getIncludeInstructions().isEmpty() ||
                        (r.getInstructions() != null && searchCriteria.getIncludeInstructions().stream().allMatch(regexPattern ->
                                r.getInstructions().stream().anyMatch(instruction -> instruction.matches(regexPattern)))))
                .filter(r -> searchCriteria.getExcludeInstructions() == null || searchCriteria.getExcludeInstructions().isEmpty() ||
                        (r.getInstructions() != null && searchCriteria.getExcludeInstructions().stream().noneMatch(regexPattern ->
                                r.getInstructions().stream().anyMatch(instruction -> instruction.matches(regexPattern)))))
                .collect(Collectors.toList());
    }
}

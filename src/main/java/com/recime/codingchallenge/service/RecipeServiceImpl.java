package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.CreateRecipeDto;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import com.recime.codingchallenge.exception.RecipeNotFoundException;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        // Convert Iterable to List since it's the industry standard return type for a collection of objects
        log.info("Fetching all recipes from the repository");
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
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
    public List<Recipe> searchRecipes(Boolean vegetarian, Integer servings, List<String> includeIngredients, List<String> excludeIngredients, List<String> instructions) {
        log.info("Searching recipes with filters - vegetarian: {}, servings: {}, includeIngredients: {}, excludeIngredients: {}, instructions: {}",
                 vegetarian, servings, includeIngredients, excludeIngredients, instructions);

        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);

        return recipes.stream()
                .filter(r -> vegetarian == null || r.isVegetarian() == vegetarian)
                .filter(r -> servings == null || r.getServings() == servings)
                .filter(r -> includeIngredients == null || includeIngredients.isEmpty() ||
                        (r.getIngredients() != null && includeIngredients.stream().allMatch(inc ->
                                r.getIngredients().stream().anyMatch(ing -> ing.getName().equalsIgnoreCase(inc)))))
                .filter(r -> excludeIngredients == null || excludeIngredients.isEmpty() ||
                        (r.getIngredients() != null && excludeIngredients.stream().noneMatch(exc ->
                                r.getIngredients().stream().anyMatch(ing -> ing.getName().equalsIgnoreCase(exc)))))
                .filter(r -> instructions == null || instructions.isEmpty() ||
                        (r.getInstructions() != null && instructions.stream().allMatch(searchTerm ->
                                r.getInstructions().stream().anyMatch(instr ->
                                        instr.toLowerCase().contains(searchTerm.toLowerCase())))))
                .collect(Collectors.toList());
    }
}

package com.recime.codingchallenge.service;

import com.recime.codingchallenge.exception.RecipeNotFoundException;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("recipeService")
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
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
    // TODO: Add handling for recipe not found
    public Recipe getRecipeById(String id) {
        log.info("Fetching recipe with ID: {}", id);
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    // TODO: Add handling for duplicate recipes
    public Recipe createRecipe(Recipe recipe) {
        log.info("Saving recipe: {}", recipe);
        return recipeRepository.save(recipe);
    }

    @Override
    // TODO: Update only the fields that are provided in the request body
    public Recipe updateRecipe(String id, Recipe recipe) {
        log.info("Updating recipe with ID: {}", id);
        Recipe origRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        origRecipe.updateWithNonNullFields(recipe);
        return recipeRepository.save(origRecipe);
    }

    @Override
    public void deleteRecipe(String id) {
        log.info("Deleting recipe with ID: {}", id);
        recipeRepository.deleteById(id);
    }
}

package com.recime.codingchallenge.service;

import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("recipeService")
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        log.info("Saving recipe: {}", recipe);
        return recipeRepository.save(recipe);
    }
}

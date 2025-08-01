package com.recime.codingchallenge.controller;

import com.recime.codingchallenge.dto.CreateReplaceRecipeDto;
import com.recime.codingchallenge.dto.RecipeDto;
import com.recime.codingchallenge.dto.RecipeSearchCriteria;
import com.recime.codingchallenge.dto.UpdateRecipeDto;
import com.recime.codingchallenge.exception.InvalidSortDirectionException;
import com.recime.codingchallenge.exception.InvalidSortPropertyException;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: implement PUT
@RestController
public class RecipeRestController {
    private final RecipeService recipeService;

    public RecipeRestController(@Qualifier("recipeService") RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    @ResponseStatus(HttpStatus.OK)
    public Page<RecipeDto> getRecipes(
            @RequestParam(value = "vegetarian", required = false) Boolean vegetarian,
            @RequestParam(value = "servings", required = false) Integer servings,
            @RequestParam(value = "includeIngredients", required = false) List<String> includeIngredients,
            @RequestParam(value = "excludeIngredients", required = false) List<String> excludeIngredients,
            @RequestParam(value = "includeInstructions", required = false) List<String> includeInstructions,
            @RequestParam(value = "excludeInstructions", required = false) List<String> excludeInstructions,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        if (!Recipe.ALLOWED_SORT_FIELDS.contains(sort)) {
            throw new InvalidSortPropertyException(sort);
        }

        RecipeSearchCriteria searchCriteria = RecipeSearchCriteria.builder()
                .vegetarian(vegetarian)
                .servings(servings)
                .includeIngredients(includeIngredients)
                .excludeIngredients(excludeIngredients)
                .includeInstructions(includeInstructions)
                .excludeInstructions(excludeInstructions)
                .build();

        Pageable pageable = buildPageable(page, size, sort, direction);

        if (searchCriteria.isEmpty()) {
            return recipeService.getAllRecipes(pageable);
        }
        return recipeService.searchRecipes(searchCriteria, pageable);
    }

    @GetMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto getRecipeById(@PathVariable String id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto createRecipe(@Valid @RequestBody CreateReplaceRecipeDto createReplaceRecipeDto) {
        return recipeService.createRecipe(createReplaceRecipeDto);
    }

    @PatchMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto updateRecipe(@PathVariable String id, @Valid @RequestBody UpdateRecipeDto updateRecipeDto) {
        return recipeService.updateRecipe(id, updateRecipeDto);
    }

    @PutMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecipeDto replaceRecipe(@PathVariable String id, @Valid @RequestBody CreateReplaceRecipeDto createReplaceRecipeDto) {
        return recipeService.replaceRecipe(id, createReplaceRecipeDto);
    }

    @DeleteMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
    }

    private static Pageable buildPageable(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException e) {
            // for catching invalid sort direction
            throw new InvalidSortDirectionException(direction);
        }
        return PageRequest.of(page, size, Sort.by(sortDirection, sort));
    }
}

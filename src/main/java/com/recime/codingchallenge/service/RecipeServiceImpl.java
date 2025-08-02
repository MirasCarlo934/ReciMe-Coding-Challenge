package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.RecipeRequestDto;
import com.recime.codingchallenge.dto.RecipeResponseDto;
import com.recime.codingchallenge.dto.RecipeSearchRequestDto;
import com.recime.codingchallenge.dto.RecipeUpdateRequestDto;
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
    public Page<RecipeResponseDto> getAllRecipes(Pageable pageable) {
        log.info("Fetching all recipes from the repository with pagination: {}", pageable);
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        return recipePage.map(RecipeResponseDto::from);
    }

    @Override
    public RecipeResponseDto getRecipeById(String id) {
        log.info("Fetching recipe with ID: {}", id);
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return RecipeResponseDto.from(recipe);
    }

    @Override
    public RecipeResponseDto createRecipe(RecipeRequestDto recipeRequestDto) {
        log.info("Saving recipe: {}", recipeRequestDto);
        Recipe savedRecipe = recipeRepository.save(Recipe.from(recipeRequestDto));
        return RecipeResponseDto.from(savedRecipe);
    }

    @Override
    public RecipeResponseDto updateRecipe(String id, RecipeUpdateRequestDto recipeUpdateRequestDto) {
        log.info("Updating recipe with ID: {}", id);
        Recipe origRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        origRecipe.updateWithNonNullFields(recipeUpdateRequestDto);
        Recipe updatedRecipe = recipeRepository.save(origRecipe);
        return RecipeResponseDto.from(updatedRecipe);
    }

    @Override
    public RecipeResponseDto replaceRecipe(String id, RecipeRequestDto recipeRequestDto) {
        log.info("Replacing recipe with ID: {}", id);
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException(id);
        }
        Recipe newRecipe = Recipe.from(recipeRequestDto);
        newRecipe.setId(id);
        Recipe savedRecipe = recipeRepository.save(newRecipe);
        return RecipeResponseDto.from(savedRecipe);
    }

    @Override
    public void deleteRecipe(String id) {
        log.info("Deleting recipe with ID: {}", id);
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException(id);
        }
        recipeRepository.deleteById(id);
    }

    @Override
    public Page<RecipeResponseDto> searchRecipes(RecipeSearchRequestDto recipeSearchRequestDto, Pageable pageable) {
        log.info("Searching recipes with criteria: {} and pagination: {}", recipeSearchRequestDto, pageable);

        // Search recipes based on the provided criteria
        List<Recipe> recipes = searchRecipesFromDb(recipeSearchRequestDto);

        // Separate filtering by vegetarian status since this is an application-level derived field
        List<Recipe> filteredRecipes = filterRecipesByVegetarian(recipeSearchRequestDto, recipes);

        // Apply pagination
        return paginateResults(pageable, filteredRecipes);
    }

    private List<Recipe> searchRecipesFromDb(RecipeSearchRequestDto recipeSearchRequestDto) {
        String includeIngredientsStr = toCommaSeparatedString(recipeSearchRequestDto.getIncludeIngredients());
        String excludeIngredientsStr = toCommaSeparatedString(recipeSearchRequestDto.getExcludeIngredients());
        String includeInstructionsStr = toCommaSeparatedString(recipeSearchRequestDto.getIncludeInstructions());
        String excludeInstructionsStr = toCommaSeparatedString(recipeSearchRequestDto.getExcludeInstructions());

        // Get all recipes matching the criteria
        return recipeRepository.findRecipesWithCriteria(
                recipeSearchRequestDto.getServings(),
                includeIngredientsStr,
                excludeIngredientsStr,
                includeInstructionsStr,
                excludeInstructionsStr
        );
    }

    private static List<Recipe> filterRecipesByVegetarian(RecipeSearchRequestDto recipeSearchRequestDto, List<Recipe> recipes) {
        List<Recipe> filteredRecipes = recipes;
        if (recipeSearchRequestDto.getVegetarian() != null) {
            filteredRecipes = recipes.stream()
                    .filter(r -> r.isVegetarian() == recipeSearchRequestDto.getVegetarian())
                    .collect(Collectors.toList());
        }
        return filteredRecipes;
    }

    private static PageImpl<RecipeResponseDto> paginateResults(Pageable pageable, List<Recipe> filteredRecipes) {
        int totalElements = filteredRecipes.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Recipe> pagedRecipes;
        if (startItem >= totalElements) {
            pagedRecipes = List.of();
        } else {
            int endItem = Math.min(startItem + pageSize, totalElements);
            pagedRecipes = filteredRecipes.subList(startItem, endItem);
        }

        List<RecipeResponseDto> pagedResponseDtos = pagedRecipes.stream()
                .map(RecipeResponseDto::from)
                .collect(Collectors.toList());
        return new PageImpl<>(pagedResponseDtos, pageable, totalElements);
    }

    private static String toCommaSeparatedString(List<String> list) {
        return list != null && !list.isEmpty()
                ? String.join(",", list)
                : null;
    }
}

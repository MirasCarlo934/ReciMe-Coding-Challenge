package com.recime.codingchallenge.service;

import com.recime.codingchallenge.dto.RecipeRequestDto;
import com.recime.codingchallenge.dto.RecipeResponseDto;
import com.recime.codingchallenge.dto.RecipeSearchRequestDto;
import com.recime.codingchallenge.dto.RecipeUpdateRequestDto;
import com.recime.codingchallenge.dto.IngredientDto;
import com.recime.codingchallenge.exception.RecipeNotFoundException;
import com.recime.codingchallenge.model.Recipe;
import com.recime.codingchallenge.model.Ingredient;
import com.recime.codingchallenge.model.Instruction;
import com.recime.codingchallenge.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe testRecipe;
    private RecipeRequestDto testRecipeRequestDto;
    private RecipeUpdateRequestDto testRecipeUpdateRequestDto;
    private final String TEST_RECIPE_ID = "test-recipe-id";

    @BeforeEach
    void setUp() {
        // Set up test recipe
        testRecipe = Recipe.builder()
                .id(TEST_RECIPE_ID)
                .title("Test Recipe")
                .description("Test Description")
                .servings(4)
                .ingredients(Arrays.asList(
                        Ingredient.builder()
                                .name("test ingredient")
                                .amount(1.0f)
                                .unit("cup")
                                .vegetarian(true)
                                .build()
                ))
                .instructions(Arrays.asList(
                        Instruction.builder()
                                .instruction("Test instruction")
                                .step(0)
                                .build()
                ))
                .build();

        // Set up test DTOs using setters (no builders available)
        testRecipeRequestDto = new RecipeRequestDto();
        testRecipeRequestDto.setTitle("Test Recipe");
        testRecipeRequestDto.setDescription("Test Description");
        testRecipeRequestDto.setServings(4);
        testRecipeRequestDto.setIngredients(Arrays.asList(
                IngredientDto.builder()
                        .name("test ingredient")
                        .amount(1.0f)
                        .unit("cup")
                        .vegetarian(true)
                        .build()
        ));
        testRecipeRequestDto.setInstructions(Arrays.asList("Test instruction"));

        testRecipeUpdateRequestDto = new RecipeUpdateRequestDto();
        testRecipeUpdateRequestDto.setTitle("Updated Title");
        testRecipeUpdateRequestDto.setDescription("Updated Description");
    }

    @Test
    void getAllRecipes_ShouldReturnPageOfRecipes() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = new PageImpl<>(Arrays.asList(testRecipe), pageable, 1);
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);

        // When
        Page<RecipeResponseDto> result = recipeService.getAllRecipes(pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Recipe");
        verify(recipeRepository).findAll(pageable);
    }

    @Test
    void getRecipeById_WhenRecipeExists_ShouldReturnRecipe() {
        // Given
        when(recipeRepository.findById(TEST_RECIPE_ID)).thenReturn(Optional.of(testRecipe));

        // When
        RecipeResponseDto result = recipeService.getRecipeById(TEST_RECIPE_ID);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_RECIPE_ID);
        assertThat(result.getTitle()).isEqualTo("Test Recipe");
        verify(recipeRepository).findById(TEST_RECIPE_ID);
    }

    @Test
    void getRecipeById_WhenRecipeNotExists_ShouldThrowRecipeNotFoundException() {
        // Given
        when(recipeRepository.findById(TEST_RECIPE_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> recipeService.getRecipeById(TEST_RECIPE_ID))
                .isInstanceOf(RecipeNotFoundException.class);
        verify(recipeRepository).findById(TEST_RECIPE_ID);
    }

    @Test
    void createRecipe_ShouldSaveAndReturnRecipe() {
        // Given
        when(recipeRepository.save(any(Recipe.class))).thenReturn(testRecipe);

        // When
        RecipeResponseDto result = recipeService.createRecipe(testRecipeRequestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Recipe");
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void updateRecipe_WhenRecipeExists_ShouldUpdateAndReturnRecipe() {
        // Given
        Recipe updatedRecipe = Recipe.builder()
                .id(TEST_RECIPE_ID)
                .title("Updated Title")
                .description("Updated Description")
                .servings(testRecipe.getServings())
                .ingredients(testRecipe.getIngredients())
                .instructions(testRecipe.getInstructions())
                .build();

        when(recipeRepository.findById(TEST_RECIPE_ID)).thenReturn(Optional.of(testRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(updatedRecipe);

        // When
        RecipeResponseDto result = recipeService.updateRecipe(TEST_RECIPE_ID, testRecipeUpdateRequestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Title");
        verify(recipeRepository).findById(TEST_RECIPE_ID);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void updateRecipe_WhenRecipeNotExists_ShouldThrowRecipeNotFoundException() {
        // Given
        when(recipeRepository.findById(TEST_RECIPE_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> recipeService.updateRecipe(TEST_RECIPE_ID, testRecipeUpdateRequestDto))
                .isInstanceOf(RecipeNotFoundException.class);
        verify(recipeRepository).findById(TEST_RECIPE_ID);
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    void replaceRecipe_WhenRecipeExists_ShouldReplaceAndReturnRecipe() {
        // Given
        Recipe replacedRecipe = Recipe.builder()
                .id(TEST_RECIPE_ID)
                .title(testRecipeRequestDto.getTitle())
                .description(testRecipeRequestDto.getDescription())
                .servings(testRecipeRequestDto.getServings())
                .ingredients(testRecipeRequestDto.getIngredients().stream()
                        .map(Ingredient::from)
                        .collect(Collectors.toList()))
                .instructions(testRecipeRequestDto.getInstructions().stream()
                        .map(instr -> Instruction.builder()
                                .instruction(instr)
                                .step(0)
                                .build())
                        .collect(Collectors.toList()))
                .build();

        when(recipeRepository.existsById(TEST_RECIPE_ID)).thenReturn(true);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(replacedRecipe);

        // When
        RecipeResponseDto result = recipeService.replaceRecipe(TEST_RECIPE_ID, testRecipeRequestDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(TEST_RECIPE_ID);
        verify(recipeRepository).existsById(TEST_RECIPE_ID);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void replaceRecipe_WhenRecipeNotExists_ShouldThrowRecipeNotFoundException() {
        // Given
        when(recipeRepository.existsById(TEST_RECIPE_ID)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> recipeService.replaceRecipe(TEST_RECIPE_ID, testRecipeRequestDto))
                .isInstanceOf(RecipeNotFoundException.class);
        verify(recipeRepository).existsById(TEST_RECIPE_ID);
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    void deleteRecipe_WhenRecipeExists_ShouldDeleteRecipe() {
        // Given
        when(recipeRepository.existsById(TEST_RECIPE_ID)).thenReturn(true);

        // When
        recipeService.deleteRecipe(TEST_RECIPE_ID);

        // Then
        verify(recipeRepository).existsById(TEST_RECIPE_ID);
        verify(recipeRepository).deleteById(TEST_RECIPE_ID);
    }

    @Test
    void deleteRecipe_WhenRecipeNotExists_ShouldThrowRecipeNotFoundException() {
        // Given
        when(recipeRepository.existsById(TEST_RECIPE_ID)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> recipeService.deleteRecipe(TEST_RECIPE_ID))
                .isInstanceOf(RecipeNotFoundException.class);
        verify(recipeRepository).existsById(TEST_RECIPE_ID);
        verify(recipeRepository, never()).deleteById(TEST_RECIPE_ID);
    }

    @Test
    void searchRecipes_WithNoFilters_ShouldReturnAllRecipes() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = new PageImpl<>(Arrays.asList(testRecipe), pageable, 1);
        RecipeSearchRequestDto searchRequest = RecipeSearchRequestDto.builder().build();

        when(recipeRepository.findRecipesWithCriteria(
                any(), any(), any(), any(), any(), eq(pageable)
        )).thenReturn(recipePage);

        // When
        Page<RecipeResponseDto> result = recipeService.searchRecipes(searchRequest, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(recipeRepository).findRecipesWithCriteria(
                any(), any(), any(), any(), any(), eq(pageable)
        );
    }

    @Test
    void searchRecipes_WithServingsFilter_ShouldFilterByServings() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = new PageImpl<>(Arrays.asList(testRecipe), pageable, 1);
        RecipeSearchRequestDto searchRequest = RecipeSearchRequestDto.builder()
                .servings(4)
                .build();

        when(recipeRepository.findRecipesWithCriteria(
                eq(4), any(), any(), any(), any(), eq(pageable)
        )).thenReturn(recipePage);

        // When
        Page<RecipeResponseDto> result = recipeService.searchRecipes(searchRequest, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getServings()).isEqualTo(4);
        verify(recipeRepository).findRecipesWithCriteria(
                eq(4), any(), any(), any(), any(), eq(pageable)
        );
    }

    @Test
    void searchRecipes_WithVegetarianFilter_ShouldFilterByVegetarian() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = new PageImpl<>(Arrays.asList(testRecipe), pageable, 1);
        RecipeSearchRequestDto searchRequest = RecipeSearchRequestDto.builder()
                .vegetarian(true)
                .build();

        when(recipeRepository.findRecipesWithCriteria(
                any(), any(), any(), any(), any(), eq(pageable)
        )).thenReturn(recipePage);

        // When
        Page<RecipeResponseDto> result = recipeService.searchRecipes(searchRequest, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        // Since test recipe has vegetarian ingredients, it should be vegetarian
        assertThat(result.getContent().get(0).isVegetarian()).isTrue();
        verify(recipeRepository).findRecipesWithCriteria(
                any(), any(), any(), any(), any(), eq(pageable)
        );
    }

    @Test
    void searchRecipes_WithIncludeIngredientsFilter_ShouldFilterByIngredients() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = new PageImpl<>(Arrays.asList(testRecipe), pageable, 1);
        RecipeSearchRequestDto searchRequest = RecipeSearchRequestDto.builder()
                .includeIngredients(Arrays.asList("test ingredient"))
                .build();

        when(recipeRepository.findRecipesWithCriteria(
                any(), eq("test ingredient"), any(), any(), any(), eq(pageable)
        )).thenReturn(recipePage);

        // When
        Page<RecipeResponseDto> result = recipeService.searchRecipes(searchRequest, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(recipeRepository).findRecipesWithCriteria(
                any(), eq("test ingredient"), any(), any(), any(), eq(pageable)
        );
    }

    @Test
    void searchRecipes_WithExcludeIngredientsFilter_ShouldExcludeRecipes() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = new PageImpl<>(Arrays.asList(testRecipe), pageable, 1);
        RecipeSearchRequestDto searchRequest = RecipeSearchRequestDto.builder()
                .excludeIngredients(Arrays.asList("test ingredient"))
                .build();

        when(recipeRepository.findRecipesWithCriteria(
                any(), any(), eq("test ingredient"), any(), any(), eq(pageable)
        )).thenReturn(recipePage);

        // When
        Page<RecipeResponseDto> result = recipeService.searchRecipes(searchRequest, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        verify(recipeRepository).findRecipesWithCriteria(
                any(), any(), eq("test ingredient"), any(), any(), eq(pageable)
        );
    }
}

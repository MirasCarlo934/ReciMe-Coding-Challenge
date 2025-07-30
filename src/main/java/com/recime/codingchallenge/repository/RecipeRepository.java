package com.recime.codingchallenge.repository;

import com.recime.codingchallenge.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    @Query("SELECT r FROM Recipe r WHERE (:servings IS NULL OR r.servings = :servings)")
    List<Recipe> findRecipesWithCriteria(
            @Param("servings") Integer servings
    );
}

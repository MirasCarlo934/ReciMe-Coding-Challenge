package com.recime.codingchallenge.repository;

import com.recime.codingchallenge.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RecipeRepository extends CrudRepository<Recipe, UUID> {

}

package com.recime.codingchallenge.repository;

import com.recime.codingchallenge.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {

}

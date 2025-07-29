package com.recime.codingchallenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Recipe {
    @Id
    private UUID id;
    private String title;
    private String description;
    private List<Ingredient> ingredients;
    private List<String> instructions;
}

package com.recime.codingchallenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    @ElementCollection
    private List<Ingredient> ingredients;
    @ElementCollection
    private List<String> instructions;
}

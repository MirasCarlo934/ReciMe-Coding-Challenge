package com.recime.codingchallenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @ElementCollection
    private List<Ingredient> ingredients;
    @ElementCollection
    private List<String> instructions;
}

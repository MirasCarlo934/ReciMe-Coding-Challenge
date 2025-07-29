package com.recime.codingchallenge.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Ingredient {
    private float amount;
    private String unit;
    private String name;
    private boolean vegetarian;
}

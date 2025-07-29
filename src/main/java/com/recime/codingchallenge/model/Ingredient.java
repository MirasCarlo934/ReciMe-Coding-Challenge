package com.recime.codingchallenge.model;

import lombok.Data;

@Data
public class Ingredient {
    private float amount;
    private String unit;
    private String name;
    private boolean vegetarian;
}
